package com.example.library_management.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BorrowInfo {
    private int id;
    private String studentCode;
    private String studentName;
    private Book book;
    private Date borrowDate;
    private Date dueDate;
    private Boolean status;

    public BorrowInfo(String studentCode, String studentName, Date dueDate) {
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.dueDate = dueDate;
    }

    public BorrowInfo(String studentCode, String studentName, Book book, Date borrowDate, Date dueDate, Boolean status) {
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
    }
}
