package com.example.inventory.dto.response.stockunit;

import com.example.inventory.type.StockUnitStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CreateStockUnitResponse {
    private String id;

    private String productId;

    private StockUnitStatus status;

    private String binId;

    private String serialNumber;

    private String batchNumber;

    private Instant expiryDate;

    private Instant createdAt;
}
