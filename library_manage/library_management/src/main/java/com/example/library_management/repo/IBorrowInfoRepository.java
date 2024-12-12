package com.example.library_management.repo;



import com.example.library_management.model.BorrowInfo;

import java.util.List;

public interface IBorrowInfoRepository {
    List<BorrowInfo> findAllPaging(int start, int size);
    Boolean borrow(BorrowInfo borrow);
    Boolean update(BorrowInfo borrow);
    Boolean delete(int id);
    Integer countRecord();

    BorrowInfo getBorrowInfo(Integer id);

    boolean restore(Integer id);

    List<BorrowInfo> search(String inputSearch, int start, int size);
}
