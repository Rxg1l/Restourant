package com.example.restourant.dto;

import com.example.restourant.model.Pemesanan;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PemesananResponseDTO {
    private Long id;
    private String nomorPesanan;
    private LocalDateTime tanggalPesan;
    private Pemesanan.StatusPemesanan status;
    private String namaPelayan;
    private Long pelayanId;
    private BigDecimal totalHarga;
    private String nomorMeja;
    private String catatan;
    private List<DetailPemesananDTO> items;

    public void setDetailPemesanan(List<DetailPemesananDTO> detailPemesanan) {
        this.items = detailPemesanan;
    }
}