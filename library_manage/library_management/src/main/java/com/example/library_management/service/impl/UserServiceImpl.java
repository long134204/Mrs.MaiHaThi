package com.example.library_management.service.impl;

import com.example.library_management.controller.PasswordUtils;
import com.example.library_management.model.User;
import com.example.library_management.repo.UserRepository;
import com.example.library_management.repo.impl.UserRepositoryImpl;
import com.example.library_management.service.UserService;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository = new UserRepositoryImpl();
    private PasswordUtils passwordUtils = new PasswordUtils();
    @Override
    public Boolean isExistEmail(String email) {
        return userRepository.isExistEmail(email);
    }

    @Override
    public void register(User user) {
        String password = passwordUtils.hashPassword(user.getPassword());
        User newUser = new User(user.getEmail(),password);
        userRepository.register(newUser);
    }

    @Override
    public Boolean login(User user) {
        String hashPassword = userRepository.getPassword(user.getEmail());
        if (passwordUtils.checkPassword(user.getPassword(), hashPassword)){
            return true;
        } else {
            return false;
        }
    }
}
