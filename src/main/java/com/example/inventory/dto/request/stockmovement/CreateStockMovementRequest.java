package com.example.inventory.dto.request.stockmovement;


import com.example.inventory.type.MovementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStockMovementRequest {
    @NotBlank(message = "Stock unit id is required")
    private String stockUnitId;

    @NotNull(message = "Movement type is required")
    private MovementType type;

    private String fromBinId;

    private String toBinId;

    private String note;
}
