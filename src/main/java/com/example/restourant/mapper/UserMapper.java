package com.example.restourant.mapper;

import com.example.restourant.dto.UserDTO;
import com.example.restourant.model.Role;
import com.example.restourant.model.User;
import com.example.restourant.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final RoleRepository roleRepository;

    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setNama(user.getNama());
        dto.setNomorTelepon(user.getNomorTelepon());
        dto.setAktif(user.isAktif());

        // Convert roles to role names (Set to List)
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        dto.setRoles(roleNames);

        return dto;
    }

    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setNama(dto.getNama());
        user.setNomorTelepon(dto.getNomorTelepon());
        user.setAktif(dto.isAktif());

        // Convert role names to Role entities
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : dto.getRoles()) {
                roleRepository.findByName(roleName)
                        .ifPresent(roles::add);
            }
            user.setRoles(roles);
        }

        return user;
    }
}