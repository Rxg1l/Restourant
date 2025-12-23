package com.example.restourant.service;

import com.example.restourant.dto.*;
import com.example.restourant.exception.ResourceNotFoundException;
import com.example.restourant.model.*;
import com.example.restourant.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PemesananService {

    private final PemesananRepository pemesananRepository;
    private final PelayanRepository pelayanRepository;
    private final MenuRepository menuRepository;

    // Create new order
    public Pemesanan createPemesanan(PemesananRequestDTO request) {
        log.info("=== SERVICE: CREATE PEMESANAN ===");
        log.info("Pelayan ID: {}", request.getPelayanId());
        log.info("Items count: {}", request.getItems() != null ? request.getItems().size() : 0);

        // Debug: Check each item
        if (request.getItems() != null) {
            for (int i = 0; i < request.getItems().size(); i++) {
                log.info("Item {}: MenuID={}, Jumlah={}",
                        i,
                        request.getItems().get(i).getMenuId(),
                        request.getItems().get(i).getJumlah());
            }
        }

        // Validate pelayan
        if (request.getPelayanId() == null) {
            throw new IllegalArgumentException("Pelayan ID tidak boleh null");
        }

        Pelayan pelayan = pelayanRepository.findById(request.getPelayanId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Pelayan not found with ID: " + request.getPelayanId()));

        // Create order
        Pemesanan pemesanan = new Pemesanan();
        pemesanan.setPelayan(pelayan);
        pemesanan.setNomorMeja(request.getNomorMeja());
        pemesanan.setCatatan(request.getCatatan());

        // Gunakan status dari DTO jika ada, jika tidak gunakan DRAFT
        if (request.getStatus() != null) {
            pemesanan.setStatus(request.getStatus());
        } else {
            pemesanan.setStatus(Pemesanan.StatusPemesanan.DRAFT);
        }

        // Calculate total and create order items
        BigDecimal totalHarga = BigDecimal.ZERO;
        List<DetailPemesanan> details = new ArrayList<>();

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Items tidak boleh kosong");
        }

        for (OrderItemDTO item : request.getItems()) {
            log.info("Processing item: MenuID={}, Jumlah={}", item.getMenuId(), item.getJumlah());

            if (item.getMenuId() == null) {
                throw new IllegalArgumentException("Menu ID tidak boleh null");
            }

            if (item.getJumlah() == null || item.getJumlah() < 1) {
                throw new IllegalArgumentException("Jumlah harus minimal 1");
            }

            Menu menu = menuRepository.findById(item.getMenuId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu not found with ID: " + item.getMenuId()));

            DetailPemesanan detail = new DetailPemesanan();
            detail.setPemesanan(pemesanan);
            detail.setMenu(menu);
            detail.setJumlah(item.getJumlah());
            detail.setHargaSatuan(menu.getHarga());
            detail.calculateSubtotal();

            details.add(detail);
            totalHarga = totalHarga.add(detail.getSubtotal());

            log.info("Added item: {} x {} = {}",
                    menu.getNama(), item.getJumlah(), detail.getSubtotal());
        }

        pemesanan.setDetailPemesanan(details);
        pemesanan.setTotalHarga(totalHarga);

        log.info("Total harga: {}", totalHarga);

        return pemesananRepository.save(pemesanan);
    }

    // Get all orders
    public List<Pemesanan> getAllPemesanan() {
        return pemesananRepository.findAll();
    }

    // Get order by ID
    public Pemesanan getPemesananById(Long id) {
        return pemesananRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pemesanan not found: " + id));
    }

    // Update order status
    public Pemesanan updateStatus(Long id, Pemesanan.StatusPemesanan status) {
        Pemesanan pemesanan = getPemesananById(id);
        pemesanan.setStatus(status);
        return pemesananRepository.save(pemesanan);
    }

    // Delete order
    public void deletePemesanan(Long id) {
        Pemesanan pemesanan = getPemesananById(id);
        pemesananRepository.delete(pemesanan);
    }

    // Get orders by status
    public List<Pemesanan> getPemesananByStatus(Pemesanan.StatusPemesanan status) {
        return pemesananRepository.findByStatus(status);
    }

    // Get today's orders
    public List<Pemesanan> getTodayPemesanan() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        return pemesananRepository.findByTanggalPesanBetween(startOfDay, endOfDay);
    }

    // Convert entity to DTO
    public PemesananResponseDTO convertToDTO(Pemesanan pemesanan) {
        PemesananResponseDTO dto = new PemesananResponseDTO();
        dto.setId(pemesanan.getId());
        dto.setNomorPesanan(pemesanan.getNomorPesanan());
        dto.setTanggalPesan(pemesanan.getTanggalPesan());
        dto.setStatus(pemesanan.getStatus());
        dto.setTotalHarga(pemesanan.getTotalHarga());
        dto.setNomorMeja(pemesanan.getNomorMeja());
        dto.setCatatan(pemesanan.getCatatan());

        if (pemesanan.getPelayan() != null) {
            dto.setNamaPelayan(pemesanan.getPelayan().getNama());
        }

        // Convert detail pemesanan
        if (pemesanan.getDetailPemesanan() != null) {
            List<DetailPemesananDTO> detailDTOs = pemesanan.getDetailPemesanan().stream()
                    .map(this::convertDetailToDTO)
                    .collect(Collectors.toList());
            dto.setItems(detailDTOs);
        }

        return dto;
    }

    private DetailPemesananDTO convertDetailToDTO(DetailPemesanan detail) {
        DetailPemesananDTO dto = new DetailPemesananDTO();
        dto.setId(detail.getId());
        dto.setMenuId(detail.getMenu().getId());
        dto.setNamaMenu(detail.getMenu().getNama());
        dto.setJumlah(detail.getJumlah());
        dto.setHargaSatuan(detail.getHargaSatuan());
        dto.setSubtotal(detail.getSubtotal());
        return dto;
    }
}