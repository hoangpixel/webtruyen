package com.flogin.webtruyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.model.TruyenYeuThich;

@Repository
public interface TruyenYeuThichRepository extends JpaRepository<TruyenYeuThich, Integer>{
    List<TruyenYeuThich> findByTruyenAndTaiKhoan(Truyen truyen, TaiKhoan taiKhoan);
}
