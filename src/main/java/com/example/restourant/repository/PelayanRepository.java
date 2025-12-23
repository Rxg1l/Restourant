package com.example.restourant.repository;

import com.example.restourant.model.Pelayan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PelayanRepository extends JpaRepository<Pelayan, Long> {

    List<Pelayan> findByNamaContainingIgnoreCase(String nama);

}
