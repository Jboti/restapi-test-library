package com.example.library.controller;


import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookResponseDto;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Returning with simple list has less control over the response,
    // Spring infers this call as 200 OK
    @GetMapping
    @Operation(summary = "Get all books", description = "Returns a list of all books from the database")
    public List<BookResponseDto> getAllBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Returns a single book or 404 if not found")
    public ResponseEntity<BookResponseDto> getBook(
            @Parameter(description = "ID of the book to retrieve")
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    @Operation(summary = "Create new book", description = "Adds a new book to the database")
    public ResponseEntity<BookResponseDto> createBook(
            @Valid @RequestBody BookRequestDto book
    ) {
        BookResponseDto savedBook = bookService.createBook(book);
        return ResponseEntity.status(201).body(savedBook);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Updates an existing book's details or 404 if not found")
    public ResponseEntity<BookResponseDto> updateBook(
            @Parameter(description = "ID of the book to update")
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDto book
    ) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Removes book from database or 404 if not found")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to delete")
            @PathVariable Long id
    ) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
