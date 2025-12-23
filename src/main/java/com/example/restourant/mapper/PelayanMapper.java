package com.example.restourant.mapper;

import com.example.restourant.dto.PelayanDTO;
import com.example.restourant.model.Pelayan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PelayanMapper {

    public PelayanDTO toDTO(Pelayan pelayan) {
        if (pelayan == null) {
            return null;
        }

        PelayanDTO dto = new PelayanDTO();
        dto.setId(pelayan.getId());
        dto.setNama(pelayan.getNama());
        dto.setNomorTelepon(pelayan.getNomorTelepon());
        dto.setGaji(pelayan.getGaji());
        dto.setAktif(pelayan.isAktif());
        return dto;
    }

    public Pelayan toEntity(PelayanDTO dto) {
        if (dto == null) {
            return null;
        }

        Pelayan pelayan = new Pelayan();
        pelayan.setId(dto.getId());
        pelayan.setNama(dto.getNama());
        pelayan.setNomorTelepon(dto.getNomorTelepon());
        pelayan.setGaji(dto.getGaji());
        pelayan.setAktif(dto.isAktif());
        return pelayan;
    }
}