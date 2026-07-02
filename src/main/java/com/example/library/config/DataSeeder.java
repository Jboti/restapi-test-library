package com.example.library.config;

import com.example.library.entity.Book;
import com.example.library.entity.Loan;
import com.example.library.entity.LoanStatus;
import com.example.library.entity.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("seed")
public class DataSeeder implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public DataSeeder(BookRepository bookRepository,
                      MemberRepository memberRepository,
                      LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public void run(String... args) {
        // Books
        Book book1 = new Book("Clean Code", "Robert C. Martin", "9780132350884", 2008);
        Book book2 = new Book("Effective Java", "Joshua Bloch", "9780134685991", 2018);
        Book book3 = new Book("The Pragmatic Programmer", "David Thomas", "9780135957059", 2019);
        Book book4 = new Book("Domain-Driven Design", "Eric Evans", "9780321125217", 2003);
        Book book5 = new Book("Refactoring", "Martin Fowler", "9780134757599", 2018);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);

        // Members
        Member member1 = new Member("Alice", "Johnson", "alice.johnson@example.com", "555-0101");
        Member member2 = new Member("Bob", "Smith", "bob.smith@example.com", "555-0102");
        Member member3 = new Member("Carla", "Diaz", "carla.diaz@example.com", null);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        // Loans

        // Active loan, borrowed today
        Loan activeLoan = new Loan(book1, member1);
        book1.setAvailable(false);

        // Overdue loan, borrowed 20 days ago (seed-only constructor)
        Loan overdueLoan = new Loan(book2, member2, LocalDate.now().minusDays(20));
        book2.setAvailable(false);

        // Returned loan, borrowed 10 days ago, returned 3 days ago
        Loan returnedLoan = new Loan(book3, member3, LocalDate.now().minusDays(10));
        returnedLoan.setReturnDate(LocalDate.now().minusDays(3));
        returnedLoan.setStatus(LoanStatus.RETURNED);
        // book3 stays available=true - it was returned

        bookRepository.save(book1);
        bookRepository.save(book2);

        loanRepository.save(activeLoan);
        loanRepository.save(overdueLoan);
        loanRepository.save(returnedLoan);

        System.out.println("Seed data loaded: 5 books, 3 members, 3 loans (1 active, 1 overdue, 1 returned).");
    }
}