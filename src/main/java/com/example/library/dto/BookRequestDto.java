package com.example.library.dto;

public class BookRequestDto {

    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;

    // Required for Jackson to work
    public BookRequestDto() {}


    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getPublishedYear() { return publishedYear; }
    public void setPublishedYear(Integer publishedYear) { this.publishedYear = publishedYear; }
}