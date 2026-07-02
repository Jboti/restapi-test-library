package com.example.library.repository;

import com.example.library.entity.Loan;
import com.example.library.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    List<Loan> findByMemberId(Long memberId);
    List<Loan> findByBookId(Long bookId);
    List<Loan> findByDueDateBeforeAndStatusNot(LocalDate date, LoanStatus status);
}
