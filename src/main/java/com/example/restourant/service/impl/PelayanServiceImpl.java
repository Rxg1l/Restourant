package com.example.restourant.service.impl;

import com.example.restourant.dto.PelayanDTO;
import com.example.restourant.exception.ResourceNotFoundException;
import com.example.restourant.mapper.PelayanMapper;
import com.example.restourant.model.Pelayan;
import com.example.restourant.repository.PelayanRepository;
import com.example.restourant.service.PelayanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PelayanServiceImpl implements PelayanService {

    private final PelayanRepository pelayanRepository;
    private final PelayanMapper pelayanMapper;

    @Override
    public List<PelayanDTO> getAllPelayan() {
        return pelayanRepository.findAll().stream()
                .map(pelayanMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PelayanDTO getPelayanById(Long id) {
        Pelayan pelayan = pelayanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pelayan not found with id: " + id));
        return pelayanMapper.toDTO(pelayan);
    }

    @Override
    public PelayanDTO createPelayan(PelayanDTO pelayanDTO) {
        Pelayan pelayan = pelayanMapper.toEntity(pelayanDTO);
        Pelayan savedPelayan = pelayanRepository.save(pelayan);
        return pelayanMapper.toDTO(savedPelayan);
    }

    @Override
    public PelayanDTO updatePelayan(Long id, PelayanDTO pelayanDTO) {
        Pelayan existingPelayan = pelayanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pelayan not found with id: " + id));

        existingPelayan.setNama(pelayanDTO.getNama());
        existingPelayan.setNomorTelepon(pelayanDTO.getNomorTelepon());
        existingPelayan.setGaji(pelayanDTO.getGaji());
        existingPelayan.setAktif(pelayanDTO.isAktif());

        if (pelayanDTO.getGaji() != null) {
            pelayanDTO.setGaji(pelayanDTO.getGaji());
        } else {
            pelayanDTO.setGaji(0.0);
        }

        Pelayan updatedPelayan = pelayanRepository.save(existingPelayan);
        return pelayanMapper.toDTO(updatedPelayan);
    }

    @Override
    public void deletePelayan(Long id) {
        Pelayan pelayan = pelayanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pelayan not found with id: " + id));
        pelayanRepository.delete(pelayan);
    }

    @Override
    public List<PelayanDTO> searchPelayan(String keyword) {
        return pelayanRepository.findAll().stream()
                .filter(pelayan -> pelayan.getNama().toLowerCase().contains(keyword.toLowerCase()) ||
                        (pelayan.getNomorTelepon() != null &&
                                pelayan.getNomorTelepon().contains(keyword)))
                .map(pelayanMapper::toDTO)
                .collect(Collectors.toList());
    }
}