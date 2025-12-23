package com.example.restourant.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username; // Bisa login dengan username
    private String email; // Atau dengan email
    private String password;
}