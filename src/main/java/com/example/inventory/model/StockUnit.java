package com.example.inventory.model;

import com.example.inventory.type.StockUnitStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document("stock_units")
public class StockUnit {
    @Id
    private String id;

    @Indexed
    private String productId;

    @Indexed
    private String binId;

    @Indexed(unique = true)
    private String serialNumber;

    @Indexed
    private StockUnitStatus status;

    @Indexed
    private String batchNumber;

    private Instant expiryDate;

    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}