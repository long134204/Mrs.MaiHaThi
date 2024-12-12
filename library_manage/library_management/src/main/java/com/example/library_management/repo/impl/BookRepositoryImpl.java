package com.example.library_management.repo.impl;



import com.example.library_management.data.BaseRepo;
import com.example.library_management.model.Book;
import com.example.library_management.model.Type;
import com.example.library_management.repo.IBookRepository;
import com.example.library_management.repo.ITypeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements IBookRepository {
    private ITypeRepository iTypeBookRepository = new TypeRepositoryImpl();
    @Override
    public List<Book> findAllPaging(int start, int size) {
        List<Book> list = new ArrayList<>();
        Book book;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book where status =? LIMIT ?, ? ");
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, start);
            preparedStatement.setInt(3, size);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                int total = resultSet.getInt("total");
                String location = resultSet.getString("location");
                int available = resultSet.getInt("available");

                List<Type> typeBooks = new ArrayList<>();
                String typeQuery = "SELECT * FROM `type` t " +
                        "JOIN book_type bt ON t.id = bt.type_id " +
                        "WHERE bt.book_id = ?";
                PreparedStatement typeStatement = connection.prepareStatement(typeQuery);
                typeStatement.setInt(1, id);
                ResultSet typeBookResultSet = typeStatement.executeQuery();
                    Type typeBook;
                while (typeBookResultSet.next()) {
                    int typeId = typeBookResultSet.getInt("id");
                    String name = typeBookResultSet.getString("name");
                    typeBook = new Type(typeId,name);
                    typeBooks.add(typeBook);
                }

                book = new Book(id,title,author,isbn,typeBooks,available,total,location, false);
                list.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Boolean create(Book book) {
        Boolean isCreate = false;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into book(title, author, isbn, available, total, location, status) values (?,?,?,?,?,?,?)");

            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setInt(4, book.getAvailable());
            preparedStatement.setInt(5, book.getTotal());
            preparedStatement.setString(6, book.getLocation());
            preparedStatement.setBoolean(7, false);

           isCreate = preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isCreate;
    }

    @Override
    public Book findByISBn(String isbn) {
        Book book = null;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book where book.isbn = ?");
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int total = resultSet.getInt("total");
                String location = resultSet.getString("location");
                int available = resultSet.getInt("available");
                Boolean status = resultSet.getBoolean("status");

                List<Type> typeBooks = new ArrayList<>();
                String typeQuery = "SELECT * FROM type t " +
                        "JOIN book_type bt ON t.id = bt.type_id " +
                        "WHERE bt.book_id = ?";
                PreparedStatement typeStatement = connection.prepareStatement(typeQuery);
                typeStatement.setInt(1, id);
                ResultSet typeBookResultSet = typeStatement.executeQuery();
                Type typeBook;
                while (typeBookResultSet.next()) {
                    int typeId = typeBookResultSet.getInt("id");
                    String name = typeBookResultSet.getString("name");
                    typeBook = new Type(typeId,name);
                    typeBooks.add(typeBook);
                }

                book = new Book(id,title,author,isbn,typeBooks,available,total,location,status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    @Override
    public Book findById(int id) {
        Book book = null;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book where book.id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int total = resultSet.getInt("total");
                String location = resultSet.getString("location");
                int available = resultSet.getInt("available");
                String isbn = resultSet.getString("isbn");
                Boolean status = resultSet.getBoolean("status");

                List<Type> typeBooks = new ArrayList<>();
                String typeQuery = "SELECT * FROM type t " +
                        "JOIN book_type bt ON t.id = bt.type_id " +
                        "WHERE bt.book_id = ?";
                PreparedStatement typeStatement = connection.prepareStatement(typeQuery);
                typeStatement.setInt(1, id);
                ResultSet typeBookResultSet = typeStatement.executeQuery();
                Type typeBook;
                while (typeBookResultSet.next()) {
                    int typeId = typeBookResultSet.getInt("id");
                    String name = typeBookResultSet.getString("name");
                    typeBook = new Type(typeId,name);
                    typeBooks.add(typeBook);
                }

                book = new Book(id,title,author,isbn,typeBooks,available,total,location,status);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    @Override
    public Boolean update(Book newBook) {
        Boolean  isUpdate = false;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `book` SET `title` = ?, `author` = ?, `isbn` = ?, `available` = ?, `total` = ?, `location` = ?, `status`=? WHERE (`id` = ?);");
            preparedStatement.setString(1,newBook.getTitle());
            preparedStatement.setString(2,newBook.getAuthor());
            preparedStatement.setString(3,newBook.getIsbn());
            preparedStatement.setInt(4,newBook.getAvailable());
            preparedStatement.setInt(5,newBook.getTotal());
            preparedStatement.setString(6,newBook.getLocation());
            preparedStatement.setBoolean(7,false);
            preparedStatement.setInt(8,newBook.getId());

            isUpdate = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isUpdate;
    }

    @Override
    public Integer getIdBookByIsbn(String isbn) {
        Integer bookId = null;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select id from `book` where isbn = ?");
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookId;
    }

    @Override
    public Boolean isValidISBN(String isbn, int id) {
        Boolean isValid = true;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from `book` where isbn = ? and id != ?");
            preparedStatement.setString(1, isbn);
            preparedStatement.setInt(2, id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                isValid = false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isValid;
    }

    @Override
    public Boolean delete(int id) {
        Boolean isDelete= false;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement p = connection.prepareStatement("UPDATE  book set `status` = ? where id = ?");
            p.setBoolean(1, true);
            p.setInt(2, id);
            isDelete= p.executeUpdate()> 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isDelete;
    }

    @Override
    public List<Book> search(String inputSearch, int page, int size) {
        List<Book> list = new ArrayList<>();
        Book book;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book where available >0 and (book.id like ? or book.title like ?) and `status` =? LIMIT ?, ?" );
            preparedStatement.setString(1, inputSearch);
            preparedStatement.setString(2, "%"+ inputSearch + "%");
            preparedStatement.setBoolean(3, false);
            preparedStatement.setInt(4, page);
            preparedStatement.setInt(5, size);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                int total = resultSet.getInt("total");
                String location = resultSet.getString("location");
                int available = resultSet.getInt("available");
                Boolean status = resultSet.getBoolean("status");
                List<Type> typeBooks = new ArrayList<>();
                String typeQuery = "SELECT * FROM `type` t " +
                        "JOIN book_type bt ON t.id = bt.type_id " +
                        "WHERE bt.book_id = ?";
                PreparedStatement typeStatement = connection.prepareStatement(typeQuery);
                typeStatement.setInt(1, id);
                ResultSet typeBookResultSet = typeStatement.executeQuery();
                Type typeBook;
                while (typeBookResultSet.next()) {
                    int typeId = typeBookResultSet.getInt("id");
                    String name = typeBookResultSet.getString("name");
                    typeBook = new Type(typeId,name);
                    typeBooks.add(typeBook);
                }

                book = new Book(id,title,author,isbn,typeBooks,available,total,location,status);
                list.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Boolean checkIsbn(String isbn) {
        Boolean valid = false;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(isbn) as total FROM management_system_library.book where isbn = ?");
            preparedStatement.setString(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int isValid = resultSet.getInt("total");
                if (isValid >= 1) {
                    valid = false;
                } else {
                    valid = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return valid;
    }

    @Override
    public Integer countRecord() {
        Integer total=0;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(id) as total from `book` where status = ?");
            preparedStatement.setBoolean(1, false);
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
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        Book book;
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book where `status`=?");
            preparedStatement.setBoolean(1,false);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                int total = resultSet.getInt("total");
                String location = resultSet.getString("location");
                int available = resultSet.getInt("available");
                Boolean status = resultSet.getBoolean("status");
                List<Type> typeBooks = new ArrayList<>();
                String typeQuery = "SELECT * FROM `type` t " +
                        "JOIN book_type bt ON t.id = bt.type_id " +
                        "WHERE bt.book_id = ?";
                PreparedStatement typeStatement = connection.prepareStatement(typeQuery);
                typeStatement.setInt(1, id);
                ResultSet typeBookResultSet = typeStatement.executeQuery();
                Type typeBook;
                while (typeBookResultSet.next()) {
                    int typeId = typeBookResultSet.getInt("id");
                    String name = typeBookResultSet.getString("name");
                    typeBook = new Type(typeId,name);
                    typeBooks.add(typeBook);
                }
                book = new Book(id,title,author,isbn,typeBooks,available,total,location,status);
                list.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public void decreaseQuantity(int bookId, int newQuantity) {
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Update book set available = ? where id =?");
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void increaseAvailable(int bookId, int available) {
        try {
            Connection connection = BaseRepo.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Update book set available = ? where id =?");
            preparedStatement.setInt(1, available);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
