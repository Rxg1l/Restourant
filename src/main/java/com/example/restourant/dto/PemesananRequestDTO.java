package com.example.restourant.dto;

import lombok.Data;
import java.util.List;

import com.example.restourant.model.Pemesanan;

@Data
public class PemesananRequestDTO {
    private Long pelayanId;
    private String nomorMeja;
    private String catatan;
    private List<OrderItemDTO> items;

    private Pemesanan.StatusPemesanan status; // Ubah dari String ke Enum

}