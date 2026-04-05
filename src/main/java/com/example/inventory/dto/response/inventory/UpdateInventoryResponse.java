package com.example.inventory.dto.response.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
public class UpdateInventoryResponse {
    private String id;
    private String tenantId;
    private String name;
    private Integer count;
    private BigDecimal price;
    private boolean visible;
    private Instant createdAt;
    private Instant updatedAt;
}

