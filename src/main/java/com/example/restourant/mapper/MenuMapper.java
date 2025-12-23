package com.example.restourant.mapper;

import com.example.restourant.dto.MenuDTO;
import com.example.restourant.model.Menu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuMapper {

    public MenuDTO toDTO(Menu menu) {
        if (menu == null) {
            return null;
        }

        MenuDTO dto = new MenuDTO();
        dto.setId(menu.getId());
        dto.setNama(menu.getNama());
        dto.setKategori(menu.getKategori());
        dto.setHarga(menu.getHarga());
        dto.setDeskripsi(menu.getDeskripsi());
        dto.setTersedia(menu.isTersedia());
        return dto;
    }

    public Menu toEntity(MenuDTO dto) {
        if (dto == null) {
            return null;
        }

        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setNama(dto.getNama());
        menu.setKategori(dto.getKategori());
        menu.setHarga(dto.getHarga());
        menu.setDeskripsi(dto.getDeskripsi());
        menu.setTersedia(dto.isTersedia());
        return menu;
    }
}