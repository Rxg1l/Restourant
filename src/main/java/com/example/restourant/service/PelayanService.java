package com.example.restourant.service;

// import com.example.restourant.dto.CustomeResponse;
import com.example.restourant.dto.PelayanDTO;

// import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Object yang dikelola
public interface PelayanService {

    List<PelayanDTO> getAllPelayan();

    // CustomeResponse<List<PelayanDTO>> getAllPelayanPaginated(Pageable pageable);

    PelayanDTO getPelayanById(Long id);

    PelayanDTO createPelayan(PelayanDTO pelayanDTO);

    PelayanDTO updatePelayan(Long id, PelayanDTO pelayanDTO);

    void deletePelayan(Long id);

    List<PelayanDTO> searchPelayan(String keyword);

}
