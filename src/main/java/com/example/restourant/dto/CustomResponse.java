package com.example.restourant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse<T> {

    private Integer status;
    private String message;
    private T data;
    private String timestamp;
    private boolean success;

    private Integer totalPage;
    private Long totalAllData;

    public static <T> CustomResponse<T> success(String message, T data) {
        return CustomResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .success(true)
                .timestamp(java.time.LocalDateTime.now()
                        .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public static <T> CustomResponse<T> success(T data) {
        return success("success", data);
    }

    public static <T> CustomResponse<T> created(String message, T data) {
        return CustomResponse.<T>builder()
                .status(HttpStatus.CREATED.value()) // Diperbaiki dari OK ke CREATED
                .message(message)
                .data(data)
                .success(true)
                .timestamp(java.time.LocalDateTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public static <T> CustomResponse<T> created(T data) {
        return created("Created", data);
    }

    public static <T> CustomResponse<T> noContent() {
        return CustomResponse.<T>builder()
                .status(HttpStatus.NO_CONTENT.value()) // Diperbaiki dari OK ke NO_CONTENT
                .message("No Content")
                .data(null)
                .success(true)
                .timestamp(java.time.LocalDateTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public static <T> CustomResponse<T> paginated(String message, T data, Integer totalPage, Long totalAllData) {
        return CustomResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .totalPage(totalPage)
                .totalAllData(totalAllData)
                .success(true)
                .timestamp(java.time.LocalDateTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public static <T> CustomResponse<T> paginated(T data, Integer totalPage, Long totalAllData) {
        return paginated("Success", data, totalPage, totalAllData);
    }

    public static <T> CustomResponse<T> error(Integer status, String message) {
        return CustomResponse.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .success(false) // Diperbaiki dari true ke false
                .timestamp(java.time.LocalDateTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public static <T> CustomResponse<T> error(HttpStatus status, String message) {
        return error(status.value(), message);
    }

    public static <T> CustomResponse<T> error(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static <T> CustomResponse<T> badRequest(String message) {
        return error(HttpStatus.BAD_REQUEST, message);
    }

    public static <T> CustomResponse<T> notFound(String message) {
        return error(HttpStatus.NOT_FOUND, message);
    }

    public static <T> CustomResponse<T> internalServerError(String message) { // Diperbaiki nama method
        return error(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    // Method ini sekarang sesuai dengan field success
    public boolean isSuccess() {
        return this.success;
    }
}