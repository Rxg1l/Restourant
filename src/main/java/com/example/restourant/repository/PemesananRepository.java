package com.example.restourant.repository;

import com.example.restourant.model.Pemesanan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PemesananRepository extends JpaRepository<Pemesanan, Long> {
    List<Pemesanan> findByStatus(Pemesanan.StatusPemesanan status);

    List<Pemesanan> findByTanggalPesanBetween(LocalDateTime start, LocalDateTime end);

    Pemesanan findByNomorPesanan(String nomorPesanan);
}