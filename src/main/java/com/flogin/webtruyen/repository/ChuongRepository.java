package com.flogin.webtruyen.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.Chuong;
import com.flogin.webtruyen.model.DanhGia;
import com.flogin.webtruyen.model.Truyen;

@Repository
public interface ChuongRepository extends JpaRepository<Chuong, Integer>{
    Chuong findTopByTruyenOrderBySoChuongDesc(Truyen truyen);
    List<Chuong> findByTruyenOrderBySoChuongDesc(Truyen truyen);
    boolean existsByTruyenIdAndSoChuong(int truyenId, int soChuong);
    Chuong findFirstByTruyenOrderBySoChuongAsc(Truyen truyen);
    Page<Chuong> findByTruyenTenTruyenContainingIgnoreCaseOrderByIdDesc(String tenTruyen, Pageable pageable);
}
