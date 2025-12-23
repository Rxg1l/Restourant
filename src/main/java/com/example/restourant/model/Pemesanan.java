package com.example.restourant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pemesanan")
public class Pemesanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nomorPesanan;

    private LocalDateTime tanggalPesan;
    private LocalDateTime tanggalUpdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'DRAFT'")
    private StatusPemesanan status;

    @ManyToOne
    @JoinColumn(name = "pelayan_id")
    private Pelayan pelayan;

    @Column(name = "waktu_pemesanan")
    private LocalDateTime waktuPemesanan;

    @OneToMany(mappedBy = "pemesanan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetailPemesanan> detailPemesanan = new ArrayList<>();

    private BigDecimal totalHarga;
    private String catatan;
    private String nomorMeja;

    // Enum untuk status
    public enum StatusPemesanan {
        DRAFT,
        DIPROSES,
        SELESAI,
        DIBATALKAN,
        DIBAYAR,
        PENDING
    }

    @PrePersist
    protected void onCreate() {
        waktuPemesanan = LocalDateTime.now();
        tanggalPesan = LocalDateTime.now();
        tanggalUpdate = LocalDateTime.now();
        nomorPesanan = generateOrderNumber();
        if (status == null) {
            status = StatusPemesanan.DRAFT;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        tanggalUpdate = LocalDateTime.now();
    }

    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }
}