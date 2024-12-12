package com.example.library_management.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private List<Type> typeBook;
    private int available;
    private int total;
    private String location;
    private Boolean status;

    public Book(String title, String author, String isbn, int available, int total, String location) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
        this.total = total;
        this.location = location;
    }

    public Book(int id, String title, String author, String isbn, int available, int total, String location) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
        this.total = total;
        this.location = location;
    }
}
