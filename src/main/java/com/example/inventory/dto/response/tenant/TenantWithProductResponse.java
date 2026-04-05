package com.example.inventory.dto.response.tenant;

import com.example.inventory.type.ProductCategory;
import com.example.inventory.type.ZoneType;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class TenantWithProductResponse {
    private String id;
    private String name;
    private Instant createdAt;
    private List<ProductSummary> products;

    @Data
    public static class ProductSummary {
        private String id;
        private String name;
        private String sku;
        private String description;
        private ProductCategory category;
        private ZoneType requiredZoneType;
    }
}
