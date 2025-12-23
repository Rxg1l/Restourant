package com.example.restourant.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetailPemesananDTO {
    private Long id;
    private Long menuId;
    private String namaMenu;
    private Integer jumlah;
    private BigDecimal hargaSatuan;
    private BigDecimal subtotal;
}