package com.example.library_management.service.impl;

import com.example.library_management.repo.IBookTypeRepository;
import com.example.library_management.repo.impl.BookTypeRepositoryImpl;
import com.example.library_management.service.IBookTypeService;

public class BookTypeServiceImpl implements IBookTypeService {
    private IBookTypeRepository bookTypeRepository = new BookTypeRepositoryImpl();
    @Override
    public void create(Integer typeId, Integer bookId) {
        bookTypeRepository.create( typeId, bookId);
    }

    @Override
    public void delete(int bookId) {
        bookTypeRepository.delete(bookId);
    }
}
