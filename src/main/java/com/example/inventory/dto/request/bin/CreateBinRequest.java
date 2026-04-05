package com.example.inventory.dto.request.bin;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateBinRequest {

    @NotBlank(message = "Zone id is required")
    private String zoneId;

    @NotBlank(message = "Code is required")
    private String code;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
}