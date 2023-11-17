package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.*;

@Table(name = "VaiTro")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ma")
    private String maVaiTro;

    @Column(name = "ten")
    private String tenVaiTro;

    @Column(name = "trang_thai")
    private Integer trangThai;


    @ManyToMany(mappedBy = "vaiTro")
    private List<TaiKhoan> taiKhoans = new ArrayList<>();

    @Override
    public String toString() {
        return "VaiTro{id=" + id + ", tenVaiTro='" + tenVaiTro + "'}";
    }


    //    @ManyToMany(mappedBy = "VaiTro" )
//    @Fetch(value= FetchMode.SELECT)
//    @JsonIgnore
//    private Set<TaiKhoan> tk = new HashSet<>();

//    @Override
//    public String toString() {
//        return tenVaiTro;
//    }
}
