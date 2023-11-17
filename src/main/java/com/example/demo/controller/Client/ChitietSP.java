package com.example.demo.controller.Client;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entity.Anh;
import com.example.demo.entity.ChiTietSanPham;
import com.example.demo.repository.ChiTietSanPhamRepository;
import com.example.demo.service.AnhService;
import com.example.demo.service.ChiTietSanPhamService;
import com.example.demo.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("")
public class ChitietSP {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private AnhService anhService;

//    @GetMapping("/{sanphamId}")
//    public String hienThiSanPham(@PathVariable UUID sanphamId, Model model) {
////        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(sanphamId).orElse(null);
////        if (chiTietSanPham != null) {
////            List<Anh> danhSachAnh = (List<Anh>) anhService.findByChiTietSanPham(chiTietSanPham);
////
////            ProductDTO productDTO = new ProductDTO();
////            productDTO.setTen(chiTietSanPham.getSanPham().getTen());
////            productDTO.setGiaBan(chiTietSanPham.getGiaBan());
////
////
////            model.addAttribute("chiTietSanPham", chiTietSanPham);
////            model.addAttribute("danhSachAnh", danhSachAnh);
////        }
//        return "SingleProduct/SingelProduct.html"; // Tên trang hiển thị sản phẩm
//    }

//    @GetMapping("/{productId}")
//    public String getProductDetail(@PathVariable UUID productId, Model model) {
        // Lấy thông tin sản phẩm từ ProductService
//        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(productId);
//
//        // Lấy danh sách ảnh sản phẩm từ ProductService
//        List<byte[]> productImages = chiTietSanPhamService.getProductImages(productId);
//
//        // Tạo đối tượng DTO để đóng gói thông tin sản phẩm và ảnh
//        ProductDetailDTO productDetailDTO = new ProductDetailDTO(
//                chiTietSanPham.getId(),
//                chiTietSanPham.getMa(),
//                productImages
//                // Thêm các thuộc tính khác của sản phẩm cần hiển thị
//        );
//
//        // Đặt thông tin sản phẩm và ảnh vào model để hiển thị trên view
//        model.addAttribute("productDetailDTO", productDetailDTO);

        // Trả về tên view (ví dụ: productDetail.html)
//        return "SingleProduct/SingelProduct.html";
//    }

    @GetMapping("/detail/{id}")
    public String getProductDetail(@PathVariable UUID id, Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.detail(id);
        model.addAttribute("chiTietSanPham", chiTietSanPham);
        return "SingleProduct/SingelProduct.html"; // Thay "productDetail" bằng tên template thực tế của bạn
    }
}
