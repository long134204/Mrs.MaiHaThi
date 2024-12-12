package com.example.library_management.service.impl;

import com.example.library_management.model.Book;
import com.example.library_management.repo.IBookRepository;
import com.example.library_management.repo.impl.BookRepositoryImpl;
import com.example.library_management.service.IBookService;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements IBookService {
    private IBookRepository bookRepository = new BookRepositoryImpl();
    @Override
    public List<Book> findAllPaging(int page, int size) {
        int start = (page - 1) * size;
        return bookRepository.findAllPaging(start, size);
    }

    @Override
    public Boolean create(Book book) {
        if (bookRepository.checkIsbn(book.getIsbn())) {
            return bookRepository.create(book);
        } else {
            return false;
        }
    }

    @Override
    public Book findByISBn(String isbn) {
        return bookRepository.findByISBn(isbn);
    }

    @Override
    public Integer getIdBookByIsbn(String isbn) {
        return bookRepository.getIdBookByIsbn(isbn);
    }

    @Override
    public Book findById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public Boolean update(Book book) {
            return bookRepository.update(book);
    }

    @Override
    public Boolean isValidISBN(String isbn, int id) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return bookRepository.delete(id);
    }

    @Override
    public List<Book> getBookById(String listBookId) {
        String[] stringIds= listBookId.split(",");
        List<Book> list = new ArrayList<>();
        Book book;
        for (String id: stringIds) {
            book = bookRepository.findById(Integer.parseInt(id));
            list.add(book);
        }
        return list;
    }

    @Override
    public Integer countRecord() {
        return bookRepository.countRecord();
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Boolean checkIsbn(String isbn) {
        return bookRepository.checkIsbn(isbn);
    }

    @Override
    public List<Book> search(String inputSearch, int page, int size) {
        int start = (page - 1) * size;
        return bookRepository.search(inputSearch, start, size);
    }

    @Override
    public void decreaseQuantity(int bookId, int newQuantity) {
        bookRepository.decreaseQuantity(bookId, newQuantity);
    }

    @Override
    public void increaseAvailable(int bookId, int available) {
        bookRepository.increaseAvailable(bookId, available );
    }


}
