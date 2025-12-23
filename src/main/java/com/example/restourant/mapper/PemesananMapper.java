package com.example.restourant.mapper;

import com.example.restourant.dto.DetailPemesananDTO;
import com.example.restourant.dto.PemesananResponseDTO;
import com.example.restourant.model.DetailPemesanan;
import com.example.restourant.model.Pemesanan;
import org.springframework.stereotype.Component;

@Component
public class PemesananMapper {

    public PemesananResponseDTO toDTO(Pemesanan pemesanan) {
        if (pemesanan == null) {
            return null;
        }

        PemesananResponseDTO dto = new PemesananResponseDTO();
        dto.setId(pemesanan.getId());
        dto.setNomorPesanan(pemesanan.getNomorPesanan());
        dto.setTanggalPesan(pemesanan.getTanggalPesan());
        dto.setStatus(pemesanan.getStatus());
        dto.setTotalHarga(pemesanan.getTotalHarga());
        dto.setNomorMeja(pemesanan.getNomorMeja());
        dto.setCatatan(pemesanan.getCatatan());

        if (pemesanan.getPelayan() != null) {
            dto.setNamaPelayan(pemesanan.getPelayan().getNama());
            dto.setPelayanId(pemesanan.getPelayan().getId());
        }

        return dto;
    }

    public DetailPemesananDTO toDetailDTO(DetailPemesanan detail) {
        if (detail == null) {
            return null;
        }

        DetailPemesananDTO dto = new DetailPemesananDTO();
        dto.setId(detail.getId());
        dto.setMenuId(detail.getMenu().getId());
        dto.setNamaMenu(detail.getMenu().getNama());
        dto.setJumlah(detail.getJumlah());
        dto.setHargaSatuan(detail.getHargaSatuan());
        dto.setSubtotal(detail.getSubtotal());
        return dto;
    }
}