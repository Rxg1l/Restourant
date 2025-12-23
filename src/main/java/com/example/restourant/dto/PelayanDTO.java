package com.example.restourant.dto;

import lombok.Data;

@Data
public class PelayanDTO {
    private Long id;
    private String nama;
    private String nomorTelepon;
    private Double gaji;
    private boolean aktif;
}