package com.flogin.webtruyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.Chuong;
import com.flogin.webtruyen.model.Truyen;

@Repository
public interface ChuongRepository extends JpaRepository<Chuong, Integer>{
    Chuong findTopByTruyenOrderBySoChuongDesc(Truyen truyen);
    List<Chuong> findByTruyenOrderBySoChuongDesc(Truyen truyen);
}
