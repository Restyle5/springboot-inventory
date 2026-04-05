package com.example.inventory.dto.request.inventory;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateInventoryRequest {

    @Size(min = 1, message = "Name cannot be empty")
    private String name;

    @Min(value = 0, message = "Count must be at least 0")
    private Integer count;

    @DecimalMin(value = "0.0", message = "Price must be at least 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have at most 2 decimal places")
    private BigDecimal price;

    private Boolean visible;
}
