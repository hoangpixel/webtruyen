package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.AnhChuong;
import com.flogin.webtruyen.model.Chuong;
import com.flogin.webtruyen.repository.AnhChuongRepository;

@Service
public class AnhChuongService {
    @Autowired
    AnhChuongRepository repoAnh;

    public List<AnhChuong> layDanhSachAnhTheoChuong(Chuong chuong)
    {
        return repoAnh.findByChuongOrderByThuTuAsc(chuong);
    }
}
