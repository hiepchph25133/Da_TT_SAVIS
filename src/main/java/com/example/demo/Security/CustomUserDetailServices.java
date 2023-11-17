package com.example.demo.Security;

import com.example.demo.entity.TaiKhoan;
import com.example.demo.entity.VaiTro;
import com.example.demo.repository.TaiKhoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailServices implements UserDetailsService {

    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    public CustomUserDetailServices(TaiKhoanRepository taiKhoanRepository) {
        this.taiKhoanRepository = taiKhoanRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        TaiKhoan tk = taiKhoanRepository.findByTenTaiKhoan(username).orElseThrow(() -> new UsernameNotFoundException("User name không tồn tại"));
//        return new User(tk.getTenTaiKhoan(),tk.getMatKhau(),mapRolesToAuthorities((List<VaiTro>) tk.getVaiTro()));
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TaiKhoan user = taiKhoanRepository.findByTenTaiKhoan(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//        return new User(user.getTenTaiKhoan(), user.getMatKhau(), mapRolesToAuthorities((List<VaiTro>) user.getVaiTro()));
        return new User(user.getTenTaiKhoan(), user.getMatKhau(), mapRolesToAuthorities(Collections.singletonList(user.getVaiTro()))
        );
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<VaiTro> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getTenVaiTro()))
                .collect(Collectors.toList());
    }



}
