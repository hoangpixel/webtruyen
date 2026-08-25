package com.flogin.webtruyen.repository;

import com.flogin.webtruyen.model.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {
    VaiTro findByTenVaiTro(String tenVaiTro);
    boolean existsByTenVaiTro(String tenVaiTro);
    boolean existsByTenVaiTroAndIdNot(String tenVaiTro, int id);
}