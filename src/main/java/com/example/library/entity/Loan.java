package com.example.library.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    private static final int LOAN_PERIOD = 14;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    protected Loan() {}

    public Loan(Book book, Member member) {
        this.book = book;
        this.member = member;
        this.borrowDate = LocalDate.now();
        this.dueDate = this.borrowDate.plusDays(LOAN_PERIOD);
        this.returnDate = null;
        this.status = LoanStatus.ACTIVE;
    }

    // ONLY FOR SEEDING / TESTING PURPOSES - TODO: DELETE LATER
    public Loan(Book book, Member member, LocalDate borrowDate) {
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(LOAN_PERIOD);
        this.returnDate = null;
        this.status = this.dueDate.isBefore(LocalDate.now())
                ? LoanStatus.OVERDUE
                : LoanStatus.ACTIVE;
    }

    public Long getId() { return id; }

    public Book getBook() { return book; }

    public Member getMember() { return member; }

    public LocalDate getBorrowDate() { return borrowDate; }

    public LocalDate getDueDate() { return dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }
}
