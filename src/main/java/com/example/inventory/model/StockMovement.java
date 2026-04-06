package com.example.inventory.model;

import com.example.inventory.type.MovementType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document("stock_movements")
public class StockMovement {
    @Id
    private String id;

    @Indexed
    private String stockUnitId;

    @Indexed
    private MovementType type;

    private String fromBinId;
    private String toBinId;

    private String performedBy;

    private String note;

    @CreatedDate
    private Instant createdAt;
}