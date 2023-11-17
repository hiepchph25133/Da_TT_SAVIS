package com.example.demo.controller;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.AnhRepository;
import com.example.demo.service.AnhService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/Home/")
public class Home {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private AnhService anhService;

    @GetMapping()
    public String Home(Model model) {
        List<ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.getAll();
        List<ProductDTO> products = new ArrayList<>();

        for (ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
            ProductDTO productInfo = new ProductDTO();
            if (!chiTietSanPham.getAnhs().isEmpty()) {
                Anh anh = chiTietSanPham.getAnhs().get(0);
                Blob hinhAnhBlob = anh.getTen();
                if (hinhAnhBlob != null) {
                    try (InputStream inputStream = hinhAnhBlob.getBinaryStream()) {
                        byte[] hinhAnhBytes = inputStream.readAllBytes();
                        String base64Image = Base64.getEncoder().encodeToString(hinhAnhBytes);
                        productInfo.setHinhAnhUrl("data:image/png;base64," + base64Image);
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            productInfo.setId(chiTietSanPham.getId());
            productInfo.setTen(chiTietSanPham.getSanPham().getTen());
            productInfo.setGiaBan(chiTietSanPham.getGiaBan());
            products.add(productInfo);
        }
        model.addAttribute("att", products);
        return "home/home.html";
    }

//    @GetMapping("/detail/{id}")
//    public String getProductDetail(@PathVariable UUID id, Model model) {
//        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
//        model.addAttribute("chiTietSanPham", chiTietSanPham);
//        return "SingleProduct/SingelProduct.html"; // Thay "productDetail" bằng tên template thực tế của bạn
//    }

}
