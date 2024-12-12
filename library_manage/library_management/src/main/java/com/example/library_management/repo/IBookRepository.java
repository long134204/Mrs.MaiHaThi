package com.example.library_management.repo;



import com.example.library_management.model.Book;

import java.util.List;

public interface IBookRepository {
    List<Book> findAllPaging(int start, int size);
    Boolean create(Book book);

    Book findByISBn(String isbn);

    Book findById(int id);

    Boolean update(Book newBook);

    Integer getIdBookByIsbn(String isbn);

    Boolean isValidISBN(String isbn, int id);

    Boolean delete(int id);

    List<Book> search(String inputSearch, int page, int size);

    Boolean checkIsbn(String isbn);

    Integer countRecord();

    List<Book> findAll();
    void decreaseQuantity(int bookId, int newQuantity);

    void increaseAvailable(int bookId, int available);
}
