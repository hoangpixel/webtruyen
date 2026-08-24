package com.flogin.webtruyen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flogin.webtruyen.model.Truyen;

import jakarta.websocket.server.PathParam;

@Repository
public interface TruyenRepository extends JpaRepository<Truyen, Integer>{
    List<Truyen> findTop10ByOrderByLuotXemDesc();
}
