package com.example.restourant.dto;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Versi tanpa builder, langsung new object
    public static <T> ResponseEntity<CustomResponse<T>> success(T data, String message) {
        CustomResponse<T> response = createResponse(true, message, data);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<CustomResponse<T>> created(T data, String message) {
        CustomResponse<T> response = createResponse(true, message, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public static <T> ResponseEntity<CustomResponse<T>> notFound(String message) {
        CustomResponse<T> response = createResponse(false, message, null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    public static <T> ResponseEntity<CustomResponse<T>> error(String message) {
        CustomResponse<T> response = createResponse(false, message, null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // Helper method menggunakan constructor langsung (tanpa builder)
    private static <T> CustomResponse<T> createResponse(boolean success, String message, T data) {
        CustomResponse<T> response = new CustomResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(LocalDateTime.now().format(formatter));
        return response;
    }
}