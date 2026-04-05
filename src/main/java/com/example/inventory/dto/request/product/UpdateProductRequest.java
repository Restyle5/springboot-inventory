package com.example.inventory.dto.request.product;


import com.example.inventory.type.ProductCategory;
import com.example.inventory.type.ZoneType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    private String name;

    private String sku;

    private String description;

    private ProductCategory category;

    private ZoneType requiredZoneType;

    @DecimalMin(value = "0.0", message = "Weight must be at least 0")
    private BigDecimal weight;

    @DecimalMin(value = "0.0", message = "Length must be at least 0")
    private BigDecimal length;

    @DecimalMin(value = "0.0", message = "Width must be at least 0")
    private BigDecimal width;

    @DecimalMin(value = "0.0", message = "Height must be at least 0")
    private BigDecimal height;

    private String dimensionUnit;

    private String weightUnit;
}