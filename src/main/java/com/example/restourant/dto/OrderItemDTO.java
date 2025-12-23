package com.example.restourant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    @NotNull(message = "gaboleh kosong")
    private Long menuId;

    @NotNull(message = "gaboleh kosong ")
    @Min(value = -1, message = "tidak boleh kurang dari 1")
    private Integer jumlah;

    private String catatan;
}