package com.flogin.webtruyen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.flogin.webtruyen.model.Chuong;
import com.flogin.webtruyen.model.Truyen;
import com.flogin.webtruyen.repository.ChuongRepository;
import com.flogin.webtruyen.repository.TruyenRepository;

@Service
public class TruyenService {
    @Autowired
    TruyenRepository repo;

    @Autowired
    ChuongRepository repoChuong;

    @Autowired
    DanhGiaService repoDanhGia;

    public Truyen layThongTinTruyen(int id)
    {
        return repo.findById(id).orElse(null);
    }

    public List<Truyen> layDanhSachTop10TruyenHot()
    {
        return repo.findTop10ByOrderByLuotXemDesc();
    }

    public Page<Truyen> layDanhSachTheoPhanTrang(int trangHienTai, int size)
    {   
        Sort sapXepMoiNhat = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(trangHienTai - 1, size, sapXepMoiNhat);
        Page<Truyen> pageTruyen = repo.findAll(pageable);

        for(Truyen truyen : pageTruyen.getContent())
        {
            Chuong chuong = repoChuong.findTopByTruyenOrderBySoChuongDesc(truyen);
            if(chuong != null)
            {
                truyen.setChuongMoiNhat("Chapter : " + chuong.getSoChuong());
            } else
            {
                truyen.setChuongMoiNhat("Chưa có chương nào");
            }
        }
        return pageTruyen;
    }

    public Page<Truyen> timKiemCoBan(int trangHienTai, int size, String tenTruyen)
    {
        Pageable pageable = PageRequest.of(trangHienTai - 1, size);
        Page<Truyen> pageTruyen = repo.findByTenTruyenContainingOrderByIdDesc(tenTruyen, pageable);

        for(Truyen truyen : pageTruyen.getContent())
        {
            Chuong chuong = repoChuong.findTopByTruyenOrderBySoChuongDesc(truyen);
            if(chuong != null)
            {
                truyen.setChuongMoiNhat("Chapter : " + chuong.getSoChuong());
            }else
            {
                truyen.setChuongMoiNhat("Chưa có chương nào");
            }
        }
        return pageTruyen;
    }
}   
