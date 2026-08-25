package com.flogin.webtruyen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.TheLoai;

@Repository
public interface TheLoaiRepository extends JpaRepository<TheLoai, Integer>{
    boolean existsByTenTheLoai(String ten);
    boolean existsByTenTheLoaiAndIdNot(String tenTheLoai, int id);
    Page<TheLoai> findByTenTheLoaiContainingOrderByIdDesc(String tenTheLoai, Pageable pageAble);
}
