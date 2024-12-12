package com.example.library_management.service.impl;

import com.example.library_management.model.Type;
import com.example.library_management.repo.ITypeRepository;
import com.example.library_management.repo.impl.TypeRepositoryImpl;
import com.example.library_management.service.ITypeService;

import java.util.List;

public class TypeServiceImpl implements ITypeService {
    private ITypeRepository typeRepository = new TypeRepositoryImpl();
    @Override
    public Type findTypeBookById(int id) {
        return null;
    }

    @Override
    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    @Override
    public Type findByName(String name) {
        return null;
    }

}
