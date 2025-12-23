package com.example.restourant.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MenuDTO {
    private Long id;
    private String nama;
    private String kategori;
    private BigDecimal harga;
    private String deskripsi;
    private boolean tersedia;
}