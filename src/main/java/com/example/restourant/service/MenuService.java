package com.example.restourant.service;

import com.example.restourant.dto.MenuDTO;
import java.util.List;

public interface MenuService {
    List<MenuDTO> getAllMenu();

    MenuDTO getMenuById(Long id); // HAPUS Optional<>

    MenuDTO createMenu(MenuDTO menuDTO);

    MenuDTO updateMenu(Long id, MenuDTO menuDTO);

    void deleteMenu(Long id);

    List<MenuDTO> getMenuByKategori(String kategori); // TAMBAH method ini

    List<MenuDTO> searchMenu(String keyword);
}