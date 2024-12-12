package com.example.library_management.service;

import com.example.library_management.model.User;

public interface UserService {
    Boolean isExistEmail(String email);

    void register(User user);

    Boolean login(User user);
}
