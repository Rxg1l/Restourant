// File: com.example.restourant.exception.GlobalExceptionHandler.java

package com.example.restourant.exception;

import com.example.restourant.dto.CustomResponse;
import com.example.restourant.dto.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handler untuk ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse<Object>> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest request) {

        String message = exception.getMessage();

        // Menggunakan helper notFound() dari CustomResponse yang Anda buat
        CustomResponse<Object> responseBody = CustomResponse.notFound(message);

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    // 2. Handler untuk Internal Server Error (Generic 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Object>> handleGlobalException(
            Exception exception,
            WebRequest request) {

        // Logika ini akan menangani semua Exception lain yang tidak tertangkap
        // (termasuk NullPointerException, dll.)
        String message = "Terjadi Kesalahan Internal Server. Silakan cek log server. Detail: " + exception.getMessage();

        // Menggunakan helper internalServerError() dari CustomResponse
        CustomResponse<Object> responseBody = CustomResponse.internalServerError(message);

        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 3. Handler untuk AuthException
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthException(AuthException ex, HttpServletRequest request) {

        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Authentication Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // Helper method untuk mendapatkan request path
    // private String getRequestPath(WebRequest request) {
    // if (request instanceof ServletWebRequest) {
    // return ((ServletWebRequest) request).getRequest().getRequestURI();
    // }
    // return "";
    // }

}
