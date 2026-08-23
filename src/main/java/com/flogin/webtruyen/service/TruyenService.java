package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.repository.TruyenRepository;

@Service
public class TruyenService {
    @Autowired
    TruyenRepository repo;

    public List<Truyen> layDanhSach()
    {
        return repo.findAll();
    }
}
