package com.example.restourant.repository;

import com.example.restourant.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // Search by keyword in multiple fields
    @Query("SELECT b FROM Menu b WHERE " +
            "LOWER(b.nama) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Menu> findByKeyword(@Param("keyword") String keyword);

    // Tambahkan method untuk debugging
    @Query("SELECT COUNT(b) FROM Menu b")
    long countAll();

    List<Menu> findByNamaContainingIgnoreCase(String keyword);

    List<Menu> findByKategori(String kategori);

    List<Menu> findByTersedia(boolean tersedia);

}
