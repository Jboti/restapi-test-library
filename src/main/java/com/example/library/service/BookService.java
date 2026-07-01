package com.example.library.service;

import com.example.library.dto.BookRequestDto;
import com.example.library.dto.BookResponseDto;
import com.example.library.entity.Book;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookResponseDto> getBooks(){
        return bookRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Book not found with id: " + id));
        return toResponseDto(book);
    }

    public BookResponseDto createBook(BookRequestDto newBook) {
        Book book = new Book(
            newBook.getTitle(),
            newBook.getAuthor(),
            newBook.getIsbn(),
            newBook.getPublishedYear()
        );
        Book savedBook = bookRepository.save(book);
        return toResponseDto(savedBook);
    }

    public BookResponseDto updateBook(Long id, BookRequestDto updateBook) {
        Book existingBook = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        existingBook.setTitle(updateBook.getTitle());
        existingBook.setAuthor(updateBook.getAuthor());
        existingBook.setIsbn(updateBook.getIsbn());
        existingBook.setPublishedYear(updateBook.getPublishedYear());

        return toResponseDto(bookRepository.save(existingBook));
    }

    public boolean deleteBook(Long id) {
        if(bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }


    private BookResponseDto toResponseDto(Book book) {
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublishedYear(),
                book.isAvailable()
        );
    }
}
