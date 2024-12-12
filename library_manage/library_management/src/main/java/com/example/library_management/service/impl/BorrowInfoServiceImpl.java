package com.example.library_management.service.impl;

import com.example.library_management.model.BorrowInfo;
import com.example.library_management.repo.IBorrowInfoRepository;
import com.example.library_management.repo.impl.BorrowInfoRepositoryImpl;
import com.example.library_management.service.IBorrowInfoService;

import java.util.List;

public class BorrowInfoServiceImpl implements IBorrowInfoService {
    private IBorrowInfoRepository borrowInfoRepository = new BorrowInfoRepositoryImpl();
    @Override
    public List<BorrowInfo> findAllPaging(int page, int size) {
        int start = (page -1) * size;
        return borrowInfoRepository.findAllPaging(start, size);
    }

    @Override
    public Boolean borrow(BorrowInfo borrow) {
        return borrowInfoRepository.borrow(borrow);
    }

    @Override
    public Boolean update(BorrowInfo borrow) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }

    @Override
    public Integer countRecord() {
        return borrowInfoRepository.countRecord();
    }

    @Override
    public BorrowInfo getBorrowInfo(Integer id) {
        return borrowInfoRepository.getBorrowInfo(id);
    }

    @Override
    public boolean restore(Integer id) {
        return borrowInfoRepository.restore(id);
    }

    @Override
    public List<BorrowInfo> search(String inputSearch, int page, int size) {
        int start = (page -1) * size;
        return borrowInfoRepository.search(inputSearch, start, size);
    }
}
