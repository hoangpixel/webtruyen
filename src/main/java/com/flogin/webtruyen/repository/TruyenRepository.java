package com.flogin.webtruyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.Truyen;

@Repository
public interface TruyenRepository extends JpaRepository<Truyen, Integer>{
    
}
