package com.example.library_management.service;

import com.example.library_management.model.Type;

import java.util.List;

public interface ITypeService {
    Type findTypeBookById(int id);
    List<Type> findAll();

    Type findByName(String name);


}
