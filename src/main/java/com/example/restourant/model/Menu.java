package com.example.restourant.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String kategori;
    private BigDecimal harga;
    private String deskripsi;
    private boolean tersedia = true;
}