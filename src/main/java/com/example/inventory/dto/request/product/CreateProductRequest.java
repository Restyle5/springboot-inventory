package com.example.inventory.dto.request.product;


import com.example.inventory.type.ProductCategory;
import com.example.inventory.type.ZoneType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String description;

    @NotNull(message = "Category is required")
    private ProductCategory category;

    @NotNull(message = "Zone type is required")
    private ZoneType requiredZoneType;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.0", message = "Weight must be at least 0")
    private BigDecimal weight;

    @NotNull(message = "Length is required")
    @DecimalMin(value = "0.0", message = "Length must be at least 0")
    private BigDecimal length;

    @NotNull(message = "Width is required")
    @DecimalMin(value = "0.0", message = "Width must be at least 0")
    private BigDecimal width;

    @NotNull(message = "Height is required")
    @DecimalMin(value = "0.0", message = "Height must be at least 0")
    private BigDecimal height;

    @NotBlank(message = "Dimension unit is required")
    private String dimensionUnit;

    @NotBlank(message = "Weight unit is required")
    private String weightUnit;
}