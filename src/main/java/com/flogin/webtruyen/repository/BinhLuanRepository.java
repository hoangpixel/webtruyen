package com.flogin.webtruyen.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.BinhLuan;

    @Repository
    public interface BinhLuanRepository extends JpaRepository<BinhLuan, Integer>{
        int countByTruyenId(int id);
        List<BinhLuan> findByTruyenIdOrderByIdDesc(int truyenId);
        Page<BinhLuan> findByTaiKhoanHoTenContainingIgnoreCaseOrderByIdDesc(String hoTen, Pageable pageable);
        List<BinhLuan> findTop5ByOrderByIdDesc();
    }
