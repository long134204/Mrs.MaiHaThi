package com.example.library_management.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseRepo {

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        String url = "jdbc:mysql://localhost:3306/management_system_library";
        String username= "root";
        String password = "root";
        connection = DriverManager.getConnection(url,username,password);

        return connection;
    }

    public static void main(String[] args) {
        try {
            getConnection();
            System.out.println("Thành công");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
