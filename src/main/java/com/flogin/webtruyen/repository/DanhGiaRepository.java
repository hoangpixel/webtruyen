package com.flogin.webtruyen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.DanhGia;
import com.flogin.webtruyen.model.TaiKhoan;
import com.flogin.webtruyen.model.Truyen;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, Integer>{
    @Query("""
                SELECT COALESCE(SUM(d.diemSao), 0)
                FROM DanhGia d
                WHERE d.truyen.id = :id
                """)
        Integer tongSoSao(@Param("id") int id);


        DanhGia findTopByTruyenAndTaiKhoanOrderByIdDesc(Truyen truyen, TaiKhoan taiKhoan);
}
