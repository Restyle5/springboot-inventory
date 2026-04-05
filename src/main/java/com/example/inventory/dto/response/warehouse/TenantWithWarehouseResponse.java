package com.example.inventory.dto.response.warehouse;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class TenantWithWarehouseResponse {
    private String id;
    private String name;
    private Instant createdAt;
    private List<WarehouseSummary> warehouses;

    @Data
    public static class WarehouseSummary {
        private String id;
        private String name;
        private String address;
        private String createdBy;
        private Instant createdAt;
        private Instant updatedAt;
    }
}