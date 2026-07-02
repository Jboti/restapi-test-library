package com.example.library.controller;

import com.example.library.dto.LoanRequestDto;
import com.example.library.dto.LoanResponseDto;
import com.example.library.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loans", description = "Endpoint for managing loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) { this.loanService = loanService; }

    @GetMapping
    @Operation(summary = "Get all loans", description = "Returns a list of all loans from the database")
    public List<LoanResponseDto> getLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan by ID", description = "Returns a single loan or 404 if not found")
    public ResponseEntity<LoanResponseDto> getLoanById(
            @Parameter(description = "ID of the loan to retrieve")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "Get loans for member", description = "Returns a list of loans for the member or 404 if not found")
    public List<LoanResponseDto> getLoansByMember(
            @Parameter(description = "ID of the member to get loans for")
            @PathVariable Long memberId
    ) {
        return loanService.getLoansByMember(memberId);
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get loans for book", description = "Returns a list of loans for the book or 404 if not found")
    public List<LoanResponseDto> getLoansByBook(
        @Parameter(description = "ID of the book to get loans for")
        @PathVariable Long bookId
    ) {
        return loanService.getLoansByBook(bookId);
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get loans that are overdue", description = "Returns a list of loans that are overdue")
    public List<LoanResponseDto> getOverdueLoans() {
        return loanService.getOverdueLoans();
    }

    @PostMapping
    @Operation(summary = "Create new loan", description = "Adds new loan to database")
    public ResponseEntity<LoanResponseDto> createLoan(
            @Valid @RequestBody LoanRequestDto loan
    ) {
        return ResponseEntity.status(201).body(loanService.createLoan(loan));
    }

    @PostMapping("/{id}/return")
    @Operation(summary = "Returns loaned out item", description = "Updates loaned out item to returned and sets return date")
    public ResponseEntity<LoanResponseDto> returnLoanedItem(
            @Parameter(description = "ID of the loan to return")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(loanService.returnLoan(id));
    }

}
