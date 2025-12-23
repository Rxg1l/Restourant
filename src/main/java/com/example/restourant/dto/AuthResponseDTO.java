package com.example.restourant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String username;
    private String email;
    private Set<String> roles;
    private Long userId;
}
