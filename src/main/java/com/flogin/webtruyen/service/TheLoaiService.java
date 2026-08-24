package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.TheLoai;
import com.flogin.webtruyen.repository.TheLoaiRepository;

@Service
public class TheLoaiService {
    @Autowired
    TheLoaiRepository repo;

    public List<TheLoai> layDanhSach()
    {
        return repo.findAll();
    }
}
