package com.example.restourant.service.impl;

import com.example.restourant.dto.MenuDTO;
import com.example.restourant.exception.ResourceNotFoundException;
import com.example.restourant.mapper.MenuMapper;
import com.example.restourant.model.Menu;
import com.example.restourant.repository.MenuRepository;
import com.example.restourant.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    @Override
    public List<MenuDTO> getAllMenu() {
        return menuRepository.findAll().stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuDTO getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
        return menuMapper.toDTO(menu);
    }

    @Override
    public MenuDTO createMenu(MenuDTO menuDTO) {
        Menu menu = menuMapper.toEntity(menuDTO);
        Menu savedMenu = menuRepository.save(menu);
        return menuMapper.toDTO(savedMenu);
    }

    @Override
    public MenuDTO updateMenu(Long id, MenuDTO menuDTO) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));

        existingMenu.setNama(menuDTO.getNama());
        existingMenu.setKategori(menuDTO.getKategori());
        existingMenu.setHarga(menuDTO.getHarga());
        existingMenu.setDeskripsi(menuDTO.getDeskripsi());
        existingMenu.setTersedia(menuDTO.isTersedia());

        Menu updatedMenu = menuRepository.save(existingMenu);
        return menuMapper.toDTO(updatedMenu);
    }

    @Override
    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found with id: " + id));
        menuRepository.delete(menu);
    }

    @Override
    public List<MenuDTO> getMenuByKategori(String kategori) {
        return menuRepository.findByKategori(kategori).stream()
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuDTO> searchMenu(String keyword) {
        return menuRepository.findAll().stream()
                .filter(menu -> menu.getNama().toLowerCase().contains(keyword.toLowerCase()) ||
                        (menu.getKategori() != null &&
                                menu.getKategori().toLowerCase().contains(keyword.toLowerCase()))
                        ||
                        (menu.getDeskripsi() != null &&
                                menu.getDeskripsi().toLowerCase().contains(keyword.toLowerCase())))
                .map(menuMapper::toDTO)
                .collect(Collectors.toList());
    }
}