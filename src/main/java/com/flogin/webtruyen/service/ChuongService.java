package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.flogin.webtruyen.model.Chuong;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.repository.ChuongRepository;

@Service
public class ChuongService {
    @Autowired
    ChuongRepository repoChuong;

    public Page<Chuong> layDanhSachCoPhanTrang(int trangHienTai, int size)
    {
        Sort sapXepMoiNhat = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(trangHienTai - 1, size, sapXepMoiNhat);
        return repoChuong.findAll(pageable);
    }

    public Page<Chuong> timKiemCoban(int trangHienTai, int size, String tenTruyen)
    {
        Pageable pageable = PageRequest.of(trangHienTai - 1, size);
        return repoChuong.findByTruyenTenTruyenContainingIgnoreCaseOrderByIdDesc(tenTruyen, pageable);
    }

    public List<Chuong> layDanhSachChuongTheoTruyen(Truyen truyen)
    {
        return repoChuong.findByTruyenOrderBySoChuongDesc(truyen);
    }

    public Chuong layChuong(int id)
    {
        return repoChuong.findById(id).orElse(null);
    }

    public void themLuotXemChuong(Chuong chuong) 
    {
        if (chuong != null) {
            chuong.setLuotXem(chuong.getLuotXem() + 1);
            repoChuong.save(chuong);
        }
    }

    public boolean themChuong(Chuong chuong)
    {
        if(chuong != null)
        {
            repoChuong.save(chuong);
            return true;
        }
        return false;
    }

    public boolean suaChuong(Chuong chuong)
    {
        if(chuong != null)
        {
            repoChuong.save(chuong);
            return true;
        }
        return false;
    }

    public boolean xoaChuong(Chuong chuong)
    {
        if(chuong != null)
        {
            repoChuong.delete(chuong);
            return true;
        }
        return false;
    }

    public Chuong layThongTinChuong(int id)
    {
        return repoChuong.findById(id).orElse(null);
    }

    public boolean kiemTraTrungSoChuong(int truyenId, int soChuong)
    {
        return repoChuong.existsByTruyenIdAndSoChuong(truyenId, soChuong);
    }

    public Chuong layChuongDauTien(Truyen truyen)
    {
        return repoChuong.findFirstByTruyenOrderBySoChuongAsc(truyen);
    }

    public long tongChuong()
    {
        return repoChuong.count();
    }
}
