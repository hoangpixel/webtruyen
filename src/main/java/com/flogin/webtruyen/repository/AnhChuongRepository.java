package com.flogin.webtruyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.AnhChuong;
import com.flogin.webtruyen.model.Chuong;

@Repository
public interface AnhChuongRepository extends JpaRepository<AnhChuong, Integer>{
    public List<AnhChuong> findByChuongOrderByThuTuAsc(Chuong chuong);
}
