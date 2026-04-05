package com.example.inventory.model;

import com.example.inventory.type.ProductCategory;
import com.example.inventory.type.ZoneType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Document("products")
@CompoundIndex(name = "tenant_product_sku_idx", def = "{'tenantId': 1, 'sku': 1}", unique = true)
public class Product {
    @Id
    private String id;

    @Indexed
    private String tenantId;

    private String name;

    private String sku;

    private String description;

    @Indexed
    private ProductCategory category;

    private ZoneType requiredZoneType;

    private BigDecimal weight;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;

    private String dimensionUnit;
    private String weightUnit;

    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}