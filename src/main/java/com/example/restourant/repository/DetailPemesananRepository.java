package com.example.restourant.repository;

import com.example.restourant.model.DetailPemesanan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailPemesananRepository extends JpaRepository<DetailPemesanan, Long> {
}