package com.example.library.service;

import com.example.library.dto.LoanRequestDto;
import com.example.library.dto.LoanResponseDto;
import com.example.library.entity.Book;
import com.example.library.entity.Loan;
import com.example.library.entity.LoanStatus;
import com.example.library.entity.Member;
import com.example.library.exception.BookNotAvailableException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository,
                       BookRepository bookRepository, MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public List<LoanResponseDto> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public LoanResponseDto getLoanById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
        return toResponseDto(loan);
    }

    @Transactional
    public LoanResponseDto createLoan(LoanRequestDto newLoan) {
        Book book = bookRepository.findById(newLoan.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + newLoan.getBookId()));
        if(!book.isAvailable()) {
            throw new BookNotAvailableException("Book with id: '" + book.getId() + "' is not available.");
        }
        Member member = memberRepository.findById(newLoan.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + newLoan.getMemberId()));
        Loan loan = new Loan(
                book,
                member
        );
        book.setAvailable(false);
        bookRepository.save(book);
        return toResponseDto(loanRepository.save(loan));
    }

    public List<LoanResponseDto> getLoansByMember(Long memberId) {
        if(!memberRepository.existsById(memberId))
            throw new ResourceNotFoundException("Member not found with id: " + memberId);
        return loanRepository.findByMemberId(memberId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public List<LoanResponseDto> getLoansByBook(Long bookId) {
        if(!bookRepository.existsById(bookId))
            throw new ResourceNotFoundException("Book not found with id: " + bookId);
        return loanRepository.findByBookId(bookId)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public List<LoanResponseDto> getOverdueLoans() {
        return loanRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), LoanStatus.RETURNED)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Transactional
    public LoanResponseDto returnLoan(Long loanId) {
        Loan existingLoan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + loanId));
        existingLoan.setReturnDate(LocalDate.now());
        existingLoan.setStatus(LoanStatus.RETURNED);

        Book book = existingLoan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return toResponseDto(loanRepository.save(existingLoan));
    }

    private LoanResponseDto toResponseDto(Loan loan) {
        return new LoanResponseDto(
                loan.getId(),
                loan.getBook().getId(),
                loan.getBook().getTitle(),
                loan.getMember().getId(),
                loan.getMember().getFirstName() + " " + loan.getMember().getLastName(),
                loan.getBorrowDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus()
        );
    }

}
