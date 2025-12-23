package com.example.restourant.controller;

import com.example.restourant.dto.CustomResponse;
import com.example.restourant.dto.UserDTO;
import com.example.restourant.mapper.UserMapper;
import com.example.restourant.model.User;
import com.example.restourant.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // GET all users
    @GetMapping
    public ResponseEntity<CustomResponse<List<UserDTO>>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDTO> userDTOs = users.stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    CustomResponse.success("Data user berhasil diambil", userDTOs));
        } catch (Exception e) {
            log.error("Error getting all users: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomResponse.error("Error: " + e.getMessage()));
        }
    }

    // GET user by ID - HANYA SATU METHOD INI
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<UserDTO>> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

            UserDTO userDTO = userMapper.toDto(user);
            return ResponseEntity.ok(
                    CustomResponse.success("Data user berhasil diambil", userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CustomResponse.error(404, e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting user by id {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomResponse.error("Error: " + e.getMessage()));
        }
    }

    // CREATE user
    @PostMapping
    public ResponseEntity<CustomResponse<UserDTO>> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult) {

        // Validasi manual
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));

            return ResponseEntity.badRequest()
                    .body(CustomResponse.error(400, "Validasi gagal: " + errorMessage));
        }

        try {
            User user = userMapper.toEntity(userDTO);
            User createdUser = userService.createUser(user);
            UserDTO createdUserDTO = userMapper.toDto(createdUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(CustomResponse.success("User berhasil dibuat", createdUserDTO));

        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomResponse.error("Error: " + e.getMessage()));
        }
    }

    // UPDATE user
    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<UserDTO>> updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO) {
        try {
            User user = userMapper.toEntity(userDTO);
            user.setId(id); // Set ID dari path variable

            User updatedUser = userService.updateUser(user);
            UserDTO updatedUserDTO = userMapper.toDto(updatedUser);

            return ResponseEntity.ok(
                    CustomResponse.success("User berhasil diupdate", updatedUserDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CustomResponse.error(404, e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomResponse.error("Error: " + e.getMessage()));
        }
    }

    // DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(
                    CustomResponse.success("User berhasil dihapus", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CustomResponse.error(404, e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomResponse.error("Error: " + e.getMessage()));
        }
    }

    // UPDATE user status
    @PatchMapping("/{id}/status")
    public ResponseEntity<CustomResponse<UserDTO>> updateUserStatus(
            @PathVariable Long id,
            @RequestParam boolean aktif) {
        try {
            User updatedUser = userService.updateUserStatus(id, aktif);
            UserDTO userDTO = userMapper.toDto(updatedUser);

            return ResponseEntity.ok(
                    CustomResponse.success("Status user berhasil diupdate", userDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CustomResponse.error(404, e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating user status: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomResponse.error("Error: " + e.getMessage()));
        }
    }
}