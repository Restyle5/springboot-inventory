package com.example.inventory.dto.request.stockunit;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateStockUnitRequest {
    @NotBlank(message = "Product id is required")
    private String productId;

    @NotBlank(message = "Bin id is required")
    private String binId;

    @NotBlank(message = "Serial number is required")
    private String serialNumber;

    @NotBlank(message = "Batch number is required")
    private String batchNumber;

    private Instant expiryDate;
}
