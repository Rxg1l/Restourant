package com.example.restourant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "detail_pemesanan")
public class DetailPemesanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pemesanan_id")
    @JsonBackReference
    private Pemesanan pemesanan;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "kuantitas")
    private Integer jumlah;

    private BigDecimal hargaSatuan;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    // Helper method untuk hitung subtotal
    @PrePersist
    @PreUpdate
    public void calculateSubtotal() {
        if (hargaSatuan != null && jumlah != null) {
            subtotal = hargaSatuan.multiply(BigDecimal.valueOf(jumlah));
        }
    }
}