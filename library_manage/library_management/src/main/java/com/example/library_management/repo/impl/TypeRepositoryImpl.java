package com.example.library_management.repo.impl;



import com.example.library_management.data.BaseRepo;
import com.example.library_management.model.Type;
import com.example.library_management.repo.ITypeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeRepositoryImpl implements ITypeRepository {
    @Override
    public Type findTypeBookById(int id) {
        Type typeBook = null;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from type where id = ?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int typeBookId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                typeBook = new Type(typeBookId,name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typeBook;
    }

    @Override
    public List<Type> findAll() {
        List<Type> list  = new ArrayList<>();
        Type typeBook;
        try {
            Connection connection = BaseRepo.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from type");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                typeBook = new Type(id,name);
                list.add(typeBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Type findByName(String name) {
        Type typeBook = null;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from type t where t.name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String typeName = resultSet.getString("name");
                typeBook = new Type(id,typeName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typeBook;
    }

//    @Override
//    public void create(String name ) {
//        try {
//            Connection connection = BaseRepo.getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement("insert into type(name) values (?)");
//            preparedStatement.setString(1,name);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
