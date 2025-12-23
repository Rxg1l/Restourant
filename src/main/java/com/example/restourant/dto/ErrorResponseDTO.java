package com.example.restourant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    // Static builder method untuk memudahkan
    public static ErrorResponseDTOBuilder builder() {
        return new ErrorResponseDTOBuilder();
    }

    // Builder class dengan method yang benar
    public static class ErrorResponseDTOBuilder {
        private LocalDateTime timestamp;
        private Integer status;
        private String error;
        private String message;
        private String path;

        public ErrorResponseDTOBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseDTOBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public ErrorResponseDTOBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ErrorResponseDTOBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseDTOBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ErrorResponseDTO build() {
            return new ErrorResponseDTO(timestamp, status, error, message, path);
        }
    }
}