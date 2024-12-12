package com.example.library_management.repo.impl;



import com.example.library_management.data.BaseRepo;
import com.example.library_management.repo.IBookTypeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookTypeRepositoryImpl implements IBookTypeRepository {
    @Override
    public void create(Integer typeId, Integer bookId) {
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into book_type(type_id,book_id) values (?,?)");
            preparedStatement.setInt(1,typeId);
            preparedStatement.setInt(2,bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int bookId) {
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from book_type where book_id = ?");
            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
