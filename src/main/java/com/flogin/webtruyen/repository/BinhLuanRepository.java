package com.flogin.webtruyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.BinhLuan;

    @Repository
    public interface BinhLuanRepository extends JpaRepository<BinhLuan, Integer>{
        int countByTruyenId(int id);
    // Tìm theo ID truyện và sắp xếp giảm dần (mới nhất lên đầu)
        List<BinhLuan> findByTruyenIdOrderByIdDesc(int truyenId);
    }
