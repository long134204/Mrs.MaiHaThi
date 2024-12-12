package com.example.library_management.repo;


public interface IBookTypeRepository {
    void create(Integer typeId, Integer bookId);

    void delete(int bookId);
}
