package com.example.library_management.controller;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public  String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public  boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
