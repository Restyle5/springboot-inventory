package com.example.inventory.dto.response.stockunit;

import com.example.inventory.type.MovementType;
import com.example.inventory.type.StockUnitStatus;
import lombok.Data;
import java.time.Instant;
import java.util.List;

@Data
public class StockUnitWithStockMovementResponse {
    private String id;
    private String productId;
    private String binId;
    private String serialNumber;
    private StockUnitStatus status;
    private Instant updatedAt;

    private List<StockMovementSummary> stockMovements;

    @Data
    public static class StockMovementSummary
    {
        private String id;
        private MovementType movementType;
        private String fromBinId;
        private String toBinId;
        private String performedBy;
        private String note;
        private Instant createdAt;
    }

}