package com.flogin.webtruyen.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Data
@Entity
@Table(name = "truyen")
public class Truyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String tenTruyen;
    private String tacGia;
    
    @Column(columnDefinition = "TEXT")
    private String moTa;
    private String anhBia;
    private String trangThai;

    private double diemTrungBinh;
    private int tongSoDanhGia;
    private int luotXem;

    @ManyToMany
    @JoinTable(
        name = "truyen_the_loai",
        joinColumns = @JoinColumn(name = "truyen_id"),
        inverseJoinColumns = @JoinColumn(name = "the_loai_id")
    )
    private List<TheLoai> danhSachTheLoai;

    @OneToMany(mappedBy = "truyen", cascade = CascadeType.ALL)
    @OrderBy("soChuong DESC")
    private List<Chuong> danhSachChuong;

    @Transient
    private String chuongMoiNhat;

    public String getSlug() 
    {
        if (this.tenTruyen == null || this.tenTruyen.isEmpty()) {
            return "";
        }
        
        String temp = this.tenTruyen.replace("đ", "d").replace("Đ", "d");
        
        String normalized = Normalizer.normalize(temp, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        
        temp = pattern.matcher(normalized).replaceAll("");
        temp = temp.replaceAll("[^a-zA-Z0-9]", "-");
        
        return temp.toLowerCase().replaceAll("-+", "-").replaceAll("^-|-$", "");
    }
}