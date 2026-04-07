package com.example.inventory.dto.response.bin;

import com.example.inventory.type.StockUnitStatus;
import com.example.inventory.type.ZoneType;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class BinWithStockUnitResponse {
    private String Id;
    private String code;
    private String zoneId;
    private ZoneType zoneType;
    private Integer capacity;
    private boolean isEmpty;

    List<StockUnitSummary> stockUnits;

    @Data
    public static class StockUnitSummary{
        private String id;
        private String productId;
        private String serialNumber;
        private StockUnitStatus status;
        private String batchNumber;
        private Instant expiryDate;
        private Instant updatedAt;
    }
}