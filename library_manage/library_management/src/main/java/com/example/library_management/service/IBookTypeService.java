package com.example.library_management.service;

public interface IBookTypeService {
    void create(Integer typeId, Integer bookId);

    void delete(int bookId);
}
