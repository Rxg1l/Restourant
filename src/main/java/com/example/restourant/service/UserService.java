package com.example.restourant.service;

import com.example.restourant.model.User;
import com.example.restourant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // GET all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET user by ID - return Optional
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // CREATE user
    public User createUser(User user) {
        // Validasi
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username tidak boleh kosong");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email tidak boleh kosong");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong");
        }

        // Cek jika username/email sudah ada
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username sudah digunakan");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email sudah digunakan");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Set default aktif jika null
        if (!user.isAktif()) {
            user.setAktif(true);
        }

        log.info("Creating user: {} with password hash: {}",
                user.getUsername(), encodedPassword.substring(0, 20) + "...");

        return userRepository.save(user);
    }

    // UPDATE user
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Update field yang boleh diubah
        if (user.getNama() != null) {
            existingUser.setNama(user.getNama());
        }
        if (user.getNomorTelepon() != null) {
            existingUser.setNomorTelepon(user.getNomorTelepon());
        }
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            // Cek jika email baru sudah digunakan
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email sudah digunakan");
            }
            existingUser.setEmail(user.getEmail());
        }
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            log.info("Updating user");
            existingUser.setRoles(user.getRoles());
        }

        existingUser.setAktif(user.isAktif());

        return userRepository.save(existingUser);
    }

    // DELETE user
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Clear roles sebelum delete
        user.getRoles().clear();
        userRepository.save(user); // Update untuk clear join table

        // Baru delete user
        userRepository.delete(user);

        log.info("User dengan ID {} berhasil dihapus", id);
    }

    // UPDATE user status
    public User updateUserStatus(Long id, boolean aktif) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        user.setAktif(aktif);
        return userRepository.save(user);
    }
}