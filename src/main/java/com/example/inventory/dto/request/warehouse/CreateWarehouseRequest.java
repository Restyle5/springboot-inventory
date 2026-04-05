package com.example.inventory.dto.request.warehouse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateWarehouseRequest {
    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull()
    private String address;
}
