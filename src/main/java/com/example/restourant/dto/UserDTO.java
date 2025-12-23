package com.example.restourant.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private Long id;

    @NotBlank(message = "Username tidak boleh kosong")
    @Size(min = 3, max = 50, message = "Username harus 3-50 karakter")
    private String username;

    @NotBlank(message = "Password tidak boleh kosong")
    @Size(min = 6, message = "Password minimal 6 karakter")
    private String password;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Nama tidak boleh kosong")
    private String nama;

    @Pattern(regexp = "^[0-9]{10,13}$", message = "Nomor telepon harus 10-13 digit angka")
    private String nomorTelepon;

    private boolean aktif = true;

    @NotEmpty(message = "Roles tidak boleh kosong")
    private List<String> roles;
}