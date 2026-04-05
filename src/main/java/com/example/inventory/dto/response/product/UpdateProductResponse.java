package com.example.inventory.dto.response.product;

import com.example.inventory.type.ProductCategory;
import com.example.inventory.type.ZoneType;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
public class UpdateProductResponse {
    private String id;
    private String name;
    private String sku;
    private String description;
    private ProductCategory category;
    private ZoneType requiredZoneType;
    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private String dimensionUnit;
    private String weightUnit;
    private Instant updatedAt;
}
