package com.example.restourant.controller;

import com.example.restourant.dto.MenuDTO;
import com.example.restourant.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@Tag(name = "Menu", description = "API untuk manajemen menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    @Operation(summary = "Get semua menu")
    public ResponseEntity<List<MenuDTO>> getAllMenu() {
        return ResponseEntity.ok(menuService.getAllMenu());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get menu by ID")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @PostMapping
    @Operation(summary = "Buat menu baru")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO) {
        return new ResponseEntity<>(menuService.createMenu(menuDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update menu")
    public ResponseEntity<MenuDTO> updateMenu(
            @PathVariable Long id,
            @RequestBody MenuDTO menuDTO) {
        return ResponseEntity.ok(menuService.updateMenu(id, menuDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Hapus menu")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/kategori/{kategori}")
    @Operation(summary = "Get menu by kategori")
    public ResponseEntity<List<MenuDTO>> getMenuByKategori(@PathVariable String kategori) {
        return ResponseEntity.ok(menuService.getMenuByKategori(kategori));
    }

    @GetMapping("/search")
    @Operation(summary = "Cari menu")
    public ResponseEntity<List<MenuDTO>> searchMenu(@RequestParam String keyword) {
        return ResponseEntity.ok(menuService.searchMenu(keyword));
    }
}