package com.example.library_management.service;

import com.example.library_management.model.BorrowInfo;

import java.util.List;

public interface IBorrowInfoService {
    List<BorrowInfo> findAllPaging(int page, int size);
    Boolean borrow(BorrowInfo borrow);
    Boolean update(BorrowInfo borrow);
    Boolean delete(int id);
    Integer countRecord();

    BorrowInfo getBorrowInfo(Integer id);

    boolean restore(Integer id);

    List<BorrowInfo> search(String inputSearch, int page, int size);


}
