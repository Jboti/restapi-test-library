package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book updateBook) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(updateBook.getTitle());
                    existingBook.setAuthor(updateBook.getAuthor());
                    existingBook.setIsbn(updateBook.getIsbn());
                    existingBook.setPublishedYear(updateBook.getPublishedYear());
                    existingBook.setAvailable(updateBook.isAvailable());

                    return bookRepository.save(existingBook);
                });
    }

    public boolean deleteBook(Long id) {
        if(bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
