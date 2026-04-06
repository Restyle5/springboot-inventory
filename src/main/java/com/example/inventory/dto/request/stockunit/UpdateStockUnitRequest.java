package com.example.inventory.dto.request.stockunit;

import com.example.inventory.type.StockUnitStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class UpdateStockUnitRequest {
    private String binId;
    private StockUnitStatus status;
    private Instant expiryDate;
}
