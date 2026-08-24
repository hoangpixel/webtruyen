package com.flogin.webtruyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.TheLoai;

@Repository
public interface TheLoaiRepository extends JpaRepository<TheLoai, Integer>{
    
}
