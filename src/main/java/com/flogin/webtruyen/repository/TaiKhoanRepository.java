package com.flogin.webtruyen.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.flogin.webtruyen.model.TaiKhoan;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    
    boolean existsByUsernameAndPassword(String username, String password);
    
    TaiKhoan findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    TaiKhoan findByUsername(String username);
}
