package com.example.restourant.controller;

import com.example.restourant.dto.PemesananRequestDTO;
import com.example.restourant.dto.PemesananResponseDTO;
import com.example.restourant.model.Pemesanan;
import com.example.restourant.service.PemesananService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pemesanan")
@RequiredArgsConstructor
@Tag(name = "Pemesanan", description = "API untuk manajemen pemesanan")
public class PemesananController {

    private final PemesananService pemesananService;

    @PostMapping
    @Operation(summary = "Buat pemesanan baru")
    public ResponseEntity<PemesananResponseDTO> createPemesanan(@RequestBody PemesananRequestDTO request) {
        Pemesanan pemesanan = pemesananService.createPemesanan(request);
        return new ResponseEntity<>(pemesananService.convertToDTO(pemesanan),
                HttpStatus.CREATED);
    }

    // @PostMapping
    // public ResponseEntity<?> createPemesanan(@RequestBody Map<String, Object>
    // requestMap) {
    // try {
    // PemesananRequestDTO request = new PemesananRequestDTO();

    // // Convert fields
    // request.setPelayanId(Long.valueOf(requestMap.get("pelayanId").toString()));
    // request.setNomorMeja((String) requestMap.get("nomorMeja"));
    // request.setCatatan((String) requestMap.get("catatan"));

    // // Convert status jika ada - dengan null safety
    // if (requestMap.containsKey("status") && requestMap.get("status") != null) {
    // String statusStr = requestMap.get("status").toString().toUpperCase();
    // try {
    // Pemesanan.StatusPemesanan statusEnum =
    // Pemesanan.StatusPemesanan.valueOf(statusStr);
    // request.setStatus(statusEnum);
    // } catch (IllegalArgumentException e) {
    // // Jika status tidak valid, default ke DRAFT
    // request.setStatus(Pemesanan.StatusPemesanan.DRAFT);
    // }
    // } else {
    // // Default ke DRAFT jika tidak ada status
    // request.setStatus(Pemesanan.StatusPemesanan.DRAFT);
    // }

    // // Convert items
    // List<Map<String, Object>> itemsList = (List<Map<String, Object>>)
    // requestMap.get("items");
    // List<OrderItemDTO> items = itemsList.stream()
    // .map(item -> {
    // OrderItemDTO dto = new OrderItemDTO();
    // dto.setMenuId(Long.valueOf(item.get("menuId").toString()));
    // dto.setJumlah(Integer.valueOf(item.get("jumlah").toString()));
    // return dto;
    // })
    // .collect(Collectors.toList());
    // request.setItems(items);

    // // Panggil service
    // Pemesanan pemesanan = pemesananService.createPemesanan(request);

    // return ResponseEntity.ok(CustomResponse.success("Pemesanan created",
    // pemesananService.convertToDTO(pemesanan)));

    // } catch (Exception e) {
    // e.printStackTrace();
    // return ResponseEntity.status(500)
    // .body(CustomResponse.error("Error: " + e.getMessage()));
    // }
    // }

    @GetMapping
    @Operation(summary = "Get semua pemesanan")
    public ResponseEntity<List<PemesananResponseDTO>> getAllPemesanan() {
        List<PemesananResponseDTO> pemesananList = pemesananService.getAllPemesanan().stream()
                .map(pemesananService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pemesananList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get pemesanan by ID")
    public ResponseEntity<PemesananResponseDTO> getPemesananById(@PathVariable Long id) {
        Pemesanan pemesanan = pemesananService.getPemesananById(id);
        return ResponseEntity.ok(pemesananService.convertToDTO(pemesanan));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update status pemesanan")
    public ResponseEntity<PemesananResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam Pemesanan.StatusPemesanan status) {
        Pemesanan pemesanan = pemesananService.updateStatus(id, status);
        return ResponseEntity.ok(pemesananService.convertToDTO(pemesanan));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Hapus pemesanan")
    public ResponseEntity<Void> deletePemesanan(@PathVariable Long id) {
        pemesananService.deletePemesanan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get pemesanan by status")
    public ResponseEntity<List<PemesananResponseDTO>> getPemesananByStatus(
            @PathVariable Pemesanan.StatusPemesanan status) {
        List<PemesananResponseDTO> pemesananList = pemesananService.getPemesananByStatus(status).stream()
                .map(pemesananService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pemesananList);
    }

    @GetMapping("/today")
    @Operation(summary = "Get pemesanan hari ini")
    public ResponseEntity<List<PemesananResponseDTO>> getTodayPemesanan() {
        List<PemesananResponseDTO> pemesananList = pemesananService.getTodayPemesanan().stream()
                .map(pemesananService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pemesananList);
    }
}