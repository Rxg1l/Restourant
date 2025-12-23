package com.example.restourant.service;

import com.example.restourant.dto.LoginRequestDTO;
import com.example.restourant.dto.LoginResponse;
import com.example.restourant.dto.RegisterRequestDTO;
import com.example.restourant.model.User;
import com.example.restourant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ResponseEntity<?> register(RegisterRequestDTO request) {
        log.info("Registering user: {}", request.getUsername());

        // Cek jika user sudah ada
        if (userRepository.findByUsername(request.getUsername()).isPresent() ||
                userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(createErrorResponse(400, "User already exists"));
        }

        // Buat user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNama(request.getNama());
        user.setAktif(true);

        userRepository.save(user);

        // Generate token
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_USER") // Default
                .build();

        String jwtToken = jwtService.generateToken(userDetails);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .type("Bearer")
                .email(user.getEmail())
                .nama(user.getNama())
                .build();

        String role = user.getRoles().stream()
                .findFirst()
                .map(userRole -> {
                    String roleName = userRole.getName();
                    return roleName.startsWith("ROLE_")
                            ? roleName.substring(5)
                            : roleName;
                })
                .orElse("USER");
        loginResponse.setRole(role);

        return ResponseEntity.ok(createSuccessResponse("Registration successful", loginResponse));

    }

    public ResponseEntity<?> login(LoginRequestDTO request) {
        log.info("Login attempt for: {}", request.getUsername() != null ? request.getUsername() : request.getEmail());

        try {
            String loginIdentifier = request.getUsername() != null ? request.getUsername() : request.getEmail();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginIdentifier, request.getPassword()));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(userDetails);

            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found after authentication"));

            LoginResponse loginResponse = LoginResponse.builder()
                    .token(jwtToken)
                    .type("Bearer")
                    .email(user.getEmail())
                    .nama(user.getNama())
                    .build();

            String firstRole = user.getRoles().stream()
                    .findFirst()
                    .map(role -> role.getName().replace("ROLE_", ""))
                    .orElse("USER");
            loginResponse.setRole(firstRole);

            return ResponseEntity.ok(createSuccessResponse("Login successful", loginResponse));

        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            return ResponseEntity.status(401).body(createErrorResponse(401, "Bad credentials"));
        }
    }

    private Map<String, Object> createSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", message);
        response.put("data", data);
        response.put("success", true);
        return response;
    }

    private Map<String, Object> createErrorResponse(int status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("success", false);
        return response;
    }
}