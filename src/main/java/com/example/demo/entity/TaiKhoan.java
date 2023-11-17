package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "TaiKhoan")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class TaiKhoan   {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "id_vt", referencedColumnName = "id")
//    private VaiTro vaiTro;

//    @ManyToMany
//    @JoinTable(name = "TaiKhoan_VaiTro", joinColumns = @JoinColumn(name = "tai_khoan_id"), inverseJoinColumns = @JoinColumn(name = "vai_tro_id"))
//    private List<VaiTro> vaiTro = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_vt")
//    private List<VaiTro> vaiTro = new ArrayList<>();
    private VaiTro vaiTro;

//    @ManyToMany
//    @JoinTable(name = "id_vt",
//    joinColumns = @JoinColumn(name = "TaiKhoan_id"))
////        private Set<VaiTro> vaiTro = new HashSet<VaiTro>();
//        private List<VaiTro> vaiTro = new ArrayList<>();
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "id_vt"
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
//    private List<VaiTro> roles = new ArrayList<>();

//    @NotBlank(message = "Không được để trống")
    @Column(name = "ma")
    private String maTaiKhoan;

//    @NotBlank(message = "Không được để trống")
    @Column(name = "ten")
    private String tenTaiKhoan;

//    @NotBlank(message = "Không được để trống")
    @Column(name = "sdt")
    private String sdt;

//    @NotBlank(message = "Không được để trống")
    @Column(name = "email")
    private String email;

//    @NotBlank(message = "Không được để trống")
    @Column(name = "dia_chi")
    private String diaChi;


    @Column(name = "ngay_sinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySinh;


    @Column(name = "ngay_tao")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngayTao;

    @Column(name = "ngay_sua")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ngaySua;


    @Column(name = "nguoi_tao")
    private String nguoiTao;

    @Column(name = "nguoi_sua")
    private String nguoiSua;

//    @NotBlank(message = "Không được để trống")
    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "trang_thai")
    private Integer trangThai;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "auth_provider")
//    private AthenticationProvider athenticationProvider;

    @OneToMany(mappedBy = "taiKhoan",fetch = FetchType.LAZY)
    private List<HoaDon> hoaDonList;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority());
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
}
