package com.example.library_management.repo;



import com.example.library_management.model.Type;

import java.util.List;

public interface ITypeRepository {
    Type findTypeBookById(int id);
    List<Type> findAll();

    Type findByName(String name);


}
