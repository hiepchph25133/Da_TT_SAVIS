package com.example.demo.service;

import com.example.demo.entity.ChiTietKichCo;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.repository.ChiTietSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface ChiTietSanPhamService  {

//    @Autowired
//    ChiTietSanPhamRepository chiTietSanPhamRepository;
////    @Override
//    public ChiTietSanPham detail(UUID id) {
//        return chiTietSanPhamRepository.getChiTietSanPhamById(id);
//    }


    List<ChiTietSanPham> getAll();

    Page<ChiTietSanPham> PhanTrang(Integer PageNum, Integer PageNo);

    Page<ChiTietSanPham> search(String ten, Integer minTien, Integer maxTien, Integer PageNum, Integer PageNo);

    void add(ChiTietSanPham chiTietSanPham);

    ChiTietSanPham detail(UUID id);

    void delete(UUID id);

}
