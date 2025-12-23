package com.example.restourant.controller;

import com.example.restourant.dto.PelayanDTO;
import com.example.restourant.service.PelayanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pelayan")
@RequiredArgsConstructor
@Tag(name = "Pelayan", description = "API untuk manajemen pelayan")
public class PelayanController {

    private final PelayanService pelayanService;

    @GetMapping
    @Operation(summary = "Get semua pelayan")
    public ResponseEntity<List<PelayanDTO>> getAllPelayan() {
        return ResponseEntity.ok(pelayanService.getAllPelayan());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pelayan by ID")
    public ResponseEntity<PelayanDTO> getPelayanById(@PathVariable Long id) {
        return ResponseEntity.ok(pelayanService.getPelayanById(id));
    }

    @PostMapping
    @Operation(summary = "Buat pelayan baru")
    public ResponseEntity<PelayanDTO> createPelayan(@RequestBody PelayanDTO pelayanDTO) {
        return new ResponseEntity<>(pelayanService.createPelayan(pelayanDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update pelayan")
    public ResponseEntity<PelayanDTO> updatePelayan(
            @PathVariable Long id,
            @RequestBody PelayanDTO pelayanDTO) {
        return ResponseEntity.ok(pelayanService.updatePelayan(id, pelayanDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Hapus pelayan")
    public ResponseEntity<Void> deletePelayan(@PathVariable Long id) {
        pelayanService.deletePelayan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Cari pelayan")
    public ResponseEntity<List<PelayanDTO>> searchPelayan(@RequestParam String keyword) {
        return ResponseEntity.ok(pelayanService.searchPelayan(keyword));
    }
}