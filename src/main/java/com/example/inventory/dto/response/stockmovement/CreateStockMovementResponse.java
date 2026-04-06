package com.example.inventory.dto.response.stockmovement;

import com.example.inventory.type.MovementType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CreateStockMovementResponse {
    private String id;
    private String stockUnitId;
    private MovementType type;
    private String fromBinId;
    private String toBinId;
    private String note;
    private Instant createdAt;
}
