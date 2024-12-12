package com.example.library_management.repo.impl;



import com.example.library_management.data.BaseRepo;
import com.example.library_management.model.Book;
import com.example.library_management.model.BorrowInfo;
import com.example.library_management.repo.IBorrowInfoRepository;
import com.example.library_management.service.IBookService;
import com.example.library_management.service.impl.BookServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowInfoRepositoryImpl implements IBorrowInfoRepository {
    private IBookService bookService = new BookServiceImpl();
    @Override
    public List<BorrowInfo> findAllPaging(int start, int size) {
        BorrowInfo borrow ;
        List<BorrowInfo> list = new ArrayList<>();
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `borrowing` LIMIT ? ,?");
            preparedStatement.setInt(1,start);
            preparedStatement.setInt(2,size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookId = resultSet.getInt("book_id");
                Date borrowDate = resultSet.getDate("borrow_date");
                Date dueDate = resultSet.getDate("due_date");
                Boolean status = resultSet.getBoolean("status");
                String studentCode = resultSet.getString("student_code");
                String studentName = resultSet.getString("student_name");
                Book book = bookService.findById(bookId);
                borrow = new BorrowInfo(id, studentCode, studentName, book, borrowDate, dueDate, status);
                list.add(borrow);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Boolean borrow(BorrowInfo borrow) {
        Boolean isCreate = false;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `borrowing` (`book_id`, `borrow_date`, `due_date`, `status`, `student_code`, `student_name`) VALUES (?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, borrow.getBook().getId());
            Date borrowDate = new Date(borrow.getBorrowDate().getTime());
            preparedStatement.setDate(2,borrowDate );
            Date dueDate = new Date(borrow.getDueDate().getTime());
            preparedStatement.setDate(3,dueDate );
            preparedStatement.setBoolean(4, borrow.getStatus());
            preparedStatement.setString(5, borrow.getStudentCode());
            preparedStatement.setString(6, borrow.getStudentName());
            isCreate = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isCreate;
    }

    @Override
    public Boolean update(BorrowInfo borrow) {
        Boolean isUpdate = false;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `borrowing` SET `book_id` = ?, `borrow_date` = ?, `due_date` = ?, `status` = ?, `student_code` = ?, `student_name` = ? WHERE (`id` = ?); ");
            preparedStatement.setInt(1, borrow.getBook().getId());
            Date borrowDate = new Date(borrow.getBorrowDate().getTime());
            preparedStatement.setDate(2,borrowDate );
            Date dueDate = new Date(borrow.getDueDate().getTime());
            preparedStatement.setDate(3,dueDate );
            preparedStatement.setBoolean(4, borrow.getStatus());
            preparedStatement.setString(5, borrow.getStudentCode());
            preparedStatement.setString(6, borrow.getStudentCode());
            preparedStatement.setInt(7, borrow.getId());
            isUpdate = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isUpdate;
    }

    @Override
    public Boolean delete(int id) {
        Boolean isDelete = false;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `borrowing` WHERE (`id` = ? );");
            preparedStatement.setInt(1, id);
            isDelete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isDelete;
    }

    @Override
    public Integer countRecord() {
        Integer total = 0;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(id) as total FROM borrowing ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                total = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return total;
    }

    @Override
    public BorrowInfo getBorrowInfo(Integer id) {
        BorrowInfo borrowInfo = null;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from borrowing where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet= preparedStatement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                Date borrowDate = resultSet.getDate("borrow_date");
                Date dueDate = resultSet.getDate("due_date");
                Boolean status = resultSet.getBoolean("status");
                String studentCode = resultSet.getString("student_code");
                String studentName = resultSet.getString("student_name");
                Book book = bookService.findById(bookId);
                borrowInfo = new BorrowInfo(id, studentCode, studentName, book, borrowDate, dueDate, status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return borrowInfo;
    }

    @Override
    public boolean restore(Integer id) {
        Boolean flag= false;
        try {
            Connection connection  =BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `borrowing` SET `status` =? where id = ?");
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, id);
            flag = preparedStatement.executeUpdate() >0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Override
    public List<BorrowInfo> search(String inputSearch, int start, int size) {
        BorrowInfo borrow ;
        List<BorrowInfo> list = new ArrayList<>();
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `borrowing` where `student_code` like ? or `student_name` like ? LIMIT ?, ?");
            preparedStatement.setString(1,inputSearch);
            preparedStatement.setString(2,"%" +inputSearch+"%");
            preparedStatement.setInt(3, start);
            preparedStatement.setInt(4, size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bookId = resultSet.getInt("book_id");
                Date borrowDate = resultSet.getDate("borrow_date");
                Date dueDate = resultSet.getDate("due_date");
                Boolean status = resultSet.getBoolean("status");
                String studentCode = resultSet.getString("student_code");
                String studentName = resultSet.getString("student_name");
                Book book = bookService.findById(bookId);
                borrow = new BorrowInfo(id, studentCode, studentName, book, borrowDate, dueDate, status);
                list.add(borrow);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
