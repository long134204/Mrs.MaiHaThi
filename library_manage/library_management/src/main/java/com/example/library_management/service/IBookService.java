package com.example.library_management.service;

import com.example.library_management.model.Book;

import java.util.List;

public interface IBookService {
    List<Book> findAllPaging(int page, int size);
    Boolean create(Book book);

    Book findByISBn(String isbn);
    Integer getIdBookByIsbn(String isbn);


    Book findById(int id);

    Boolean update(Book Book);

    Boolean isValidISBN(String isbn, int id);

    Boolean delete(int id);

    List<Book> getBookById(String listBookId );

    Integer countRecord();

    List<Book> findAll();
    Boolean checkIsbn(String isbn);


    List<Book> search(String inputSearch, int page, int size);
    void decreaseQuantity(int bookId, int newQuantity);

    void increaseAvailable(int bookId, int available);
}
