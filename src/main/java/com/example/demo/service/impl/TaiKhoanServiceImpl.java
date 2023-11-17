package com.example.demo.service.impl;

import com.example.demo.dto.RegisterDTO;
import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.VaiTro;
import com.example.demo.repository.TaiKhoanRepository;
import com.example.demo.repository.VaiTroRepository;
import com.example.demo.service.TaiKhoanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TaiKhoanServiceImpl implements TaiKhoanService  {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<TaiKhoan> getAll() {
        return taiKhoanRepository.findAll();
    }

    @Override
    public Page<TaiKhoan> page(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return taiKhoanRepository.getAllNhanVien(pageable);
    }

    @Override
    public Page<TaiKhoan> getAllNhanVien(Integer page, Integer size) {
        Pageable pageable=PageRequest.of(page,size);
        return taiKhoanRepository.getAllNhanvien1(pageable);
    }


    @Override
    public Page<TaiKhoan> search(String ma, String ten, String sdt, String email, String diaChi, Date ngaySinh, Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        return taiKhoanRepository.search(ma, ten, sdt, diaChi, email, ngaySinh, pageable);
    }

    @Override
    public TaiKhoan detail(UUID id) {
        return taiKhoanRepository.findById(id).get();
    }

    @Override
    public TaiKhoan update(TaiKhoan taiKhoan) {
        return taiKhoanRepository.save(taiKhoan);
    }

    @Override
    public TaiKhoan add(TaiKhoan taiKhoan) {
        return taiKhoanRepository.save(taiKhoan);
    }


    @Override
    public void delete(UUID id) {
        taiKhoanRepository.deleteById(id);
    }

    @Override
    public List<TaiKhoan> getByKeyWord(String keyword) {
        return taiKhoanRepository.findByKeyWord(keyword);
    }

    @Override
    public List<TaiKhoan> getTrangThai(Integer trangthai) {
        return taiKhoanRepository.findByTrangThai(trangthai);
    }

    @Override
    public TaiKhoan getAllKhachHang(UUID id) {
        return taiKhoanRepository.getAllKhachHang(id);
    }

    @Override
    public TaiKhoan findByHoaDonId(UUID id) {
        return taiKhoanRepository.findByHoaDonId(id);
    }

    @Override
    public TaiKhoan checkLogin(TaiKhoan taiKhoan) {
        if (taiKhoanRepository.existsByTenTaiKhoanAndMatKhau(taiKhoan.getTenTaiKhoan(),taiKhoan.getMatKhau())){
            return taiKhoanRepository.findByAcc(taiKhoan.getTenTaiKhoan(),taiKhoan.getMatKhau());
        }
        return null;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        TaiKhoan taiKhoan = taiKhoanRepository.findByTenTaiKhoan(username);
//        if (taiKhoan == null){
//            log.error("Không có tai khoan trong database");
//            throw new UsernameNotFoundException("User not found in the database");
//        }else{
//            log.info("Không có tai khoan trong database :{}",username );
//        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
////        taiKhoan.getVaiTro().forEach(vt -> {
////            authorities.add(new SimpleGrantedAuthority(vt.get()));
//        });
//
//        return null;
//    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        TaiKhoan taiKhoan = taiKhoanRepository.findByTenTaiKhoan(username);
//        if (taiKhoan == null){
//            log.error("User not found in the database");
//            throw new UsernameNotFoundException("User not found in the database");
//
//        }else {
//            log.info("User not found in the database: {}",username);
//        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        taiKhoan.getVaiTro().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority(role.getTenVaiTro()));
//        });
//        return new org.springframework.security.core.userdetails.User(taiKhoan.getTenTaiKhoan(),taiKhoan.getMatKhau(),authorities);
//    }



//    @Transactional
//    public TaiKhoan createTaiKhoan(RegisterDTO registerDTO) {
//        // Kiểm tra xem tài khoản đã tồn tại chưa
//        if (taiKhoanRepository.existsByTenTaiKhoan(registerDTO.getUserName())) {
//            throw new RuntimeException("Tài khoản đã tồn tại");
//        }
//
//        // Tìm hoặc tạo một vai trò "USER"
//        VaiTro vt = vaiTroRepository.findBytenVaiTro("USER").orElseGet(() -> {
//            VaiTro newVaiTro = new VaiTro();
//            newVaiTro.setTenVaiTro("USER");
//            return vaiTroRepository.save(newVaiTro);
//        });
//
//        // Tạo tài khoản và đặt vai trò
//        TaiKhoan tk = new TaiKhoan();
//        tk.setTenTaiKhoan(registerDTO.getUserName());
//        tk.setMatKhau(passwordEncoder.encode(registerDTO.getPassword()));
//
//        // Đặt giá trị id_vt của tài khoản
//        tk.setVaiTro(vt.getId());
//
//        return taiKhoanRepository.save(tk);
//    }

//    public TaiKhoan createTaiKhoan(RegisterDTO registerDTO) {
//        // Kiểm tra xem tài khoản đã tồn tại chưa
//        if (taiKhoanRepository.existsByTenTaiKhoan(registerDTO.getUserName())) {
//            throw new RuntimeException("Tài khoản đã tồn tại");
//        }
//        // Tìm hoặc tạo một vai trò "USER"
//        VaiTro vt = vaiTroRepository.findBytenVaiTro("USER").orElseGet(() -> {
//            VaiTro newVaiTro = new VaiTro();
//            newVaiTro.setTenVaiTro("USER");
//            return vaiTroRepository.save(newVaiTro);
//        });
//        // Tạo tài khoản và đặt vai trò
//        TaiKhoan tk = new TaiKhoan();
//        tk.setTenTaiKhoan(registerDTO.getUserName());
//        tk.setMatKhau(passwordEncoder.encode(registerDTO.getPassword()));
//        List<VaiTro> danhSachVaiTro = new ArrayList<>();
//        danhSachVaiTro.add(vt);
//
////        tk.setVaiTro(danhSachVaiTro); // Đặt danh sách vai trò cho tài khoản
//        tk.setVaiTro(vt.getId()); // Đặt giá trị id_vt của tài khoản
//
//        return taiKhoanRepository.save(tk);
//    }


    @Transactional
    public TaiKhoan createTaiKhoan(RegisterDTO registerDTO) {
        // Kiểm tra xem tài khoản đã tồn tại chưa
        if (taiKhoanRepository.existsByTenTaiKhoan(registerDTO.getUserName())) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }
        // Tìm hoặc tạo một vai trò "USER"
        VaiTro vt = vaiTroRepository.findBytenVaiTro("USER").orElseGet(() -> {
            VaiTro newVaiTro = new VaiTro();
            newVaiTro.setTenVaiTro("USER");
            return vaiTroRepository.save(newVaiTro);
        });

        // Tạo tài khoản và đặt vai trò
        TaiKhoan tk = new TaiKhoan();
        tk.setTenTaiKhoan(registerDTO.getUserName());
        tk.setMatKhau(passwordEncoder.encode(registerDTO.getPassword()));

        // Đặt đối tượng VaiTro cho tài khoản
        tk.setVaiTro(vt);

        return taiKhoanRepository.save(tk);
    }

};



