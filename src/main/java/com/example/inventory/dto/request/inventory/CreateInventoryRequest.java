package com.example.inventory.dto.request.inventory;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateInventoryRequest {

    @NotBlank(message = "TenantId is required.")
    private String tenantId;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull(message = "Count is required")
    @Min(value = 0, message = "Count must be at least 0")
    private Integer count;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be at least 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 2 decimal places")
    private BigDecimal price;

    private boolean visible = true;
}