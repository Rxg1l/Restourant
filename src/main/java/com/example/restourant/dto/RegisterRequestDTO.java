package com.example.restourant.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;
    private String nama;
    private String nomorTelepon;
    private String role;

    private boolean aktif;

    public RegisterRequestDTO() {
        this.aktif = true; // Default nilai aktif adalah true
    }

    public RegisterRequestDTO(String username, String password, String email, String nama, String nomorTelepon,
            String role, boolean aktif) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.role = role;
        this.aktif = aktif;
    }

}
