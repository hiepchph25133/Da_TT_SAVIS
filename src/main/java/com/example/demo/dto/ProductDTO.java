package com.example.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.UUID;

@Getter
@Setter
@Data
public class ProductDTO {

    private UUID id;
    private String ten;
    private String hinhAnhUrl;

    private double giaBan;
}
