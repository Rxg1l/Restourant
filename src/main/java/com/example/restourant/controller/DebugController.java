package com.example.restourant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Enumeration;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/debug")
public class DebugController {

    @GetMapping("/simple-check")
    public ResponseEntity<?> simpleCheck(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Cek semua headers
        Enumeration<String> headerNames = request.getHeaderNames();
        List<String> headers = new ArrayList<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.add(headerName + ": " + request.getHeader(headerName));
        }
        response.put("headers", headers);

        // Cek Authorization header khusus
        String authHeader = request.getHeader("Authorization");
        response.put("authorization_header", authHeader);
        response.put("has_bearer", authHeader != null && authHeader.startsWith("Bearer "));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth-info")
    public ResponseEntity<?> getAuthInfo(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if (authentication != null) {
            response.put("name", authentication.getName());
            response.put("authorities", authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            response.put("authenticated", authentication.isAuthenticated());
            response.put("principal", authentication.getPrincipal().getClass().getName());
        } else {
            response.put("message", "Not authenticated");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-details")
    public ResponseEntity<?> getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();

        if (userDetails != null) {
            response.put("username", userDetails.getUsername());
            response.put("authorities", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            response.put("enabled", userDetails.isEnabled());
            response.put("accountNonExpired", userDetails.isAccountNonExpired());
            response.put("accountNonLocked", userDetails.isAccountNonLocked());
            response.put("credentialsNonExpired", userDetails.isCredentialsNonExpired());
        } else {
            response.put("message", "UserDetails is null");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/security-context")
    public ResponseEntity<?> getSecurityContext() {
        Map<String, Object> response = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            response.put("name", auth.getName());
            response.put("authorities", auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
            response.put("authenticated", auth.isAuthenticated());
            response.put("principal", auth.getPrincipal().toString());
            response.put("credentials", auth.getCredentials());
            response.put("details", auth.getDetails());
        } else {
            response.put("message", "No authentication in SecurityContext");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-menu-access")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    public ResponseEntity<?> checkMenuAccess() {
        return ResponseEntity.ok("You have access to menu endpoints!");
    }

}