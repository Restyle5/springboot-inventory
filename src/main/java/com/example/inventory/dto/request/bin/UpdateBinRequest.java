package com.example.inventory.dto.request.bin;


import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateBinRequest {

    private String zoneId;

    private String code;

    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
}