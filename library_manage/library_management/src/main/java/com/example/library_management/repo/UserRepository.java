package com.example.library_management.repo;

import com.example.library_management.model.User;

public interface UserRepository {
    Boolean isExistEmail(String email);

    void register(User user);

    String getPassword(String email);
}
