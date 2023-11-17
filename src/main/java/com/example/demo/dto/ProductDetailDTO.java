package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
public class ProductDetailDTO {


    private String tenSp;
    private float giasp;
    private String loai;
    private String hinhAnhUrl;
    private String chatlieu;
    private String mauSac;
    private String coAo;
    private String kichco;

}
