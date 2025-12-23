package com.example.restourant.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pelayan")
public class Pelayan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String nomorTelepon;

    @Column(name = "gaji", nullable = false)
    private Double gaji;

    private boolean aktif = true;

    @PrePersist
    protected void onCreate() {
        if (gaji == null) {
            gaji = 0.0;
        }
    }

}