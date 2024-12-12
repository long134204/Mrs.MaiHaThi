package com.example.library_management.repo.impl;

import com.example.library_management.data.BaseRepo;
import com.example.library_management.model.User;
import com.example.library_management.repo.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Boolean isExistEmail(String email) {
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT email from `User` where email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    @Override
    public void register(User user) {
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into user (`email`,`password`) values (?,?)");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPassword(String email) {
        String password = null;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT password from `User` where email =?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 password = resultSet.getString("password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return password;
    }
}
