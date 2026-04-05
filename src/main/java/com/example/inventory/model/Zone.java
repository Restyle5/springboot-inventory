package com.example.inventory.model;

import com.example.inventory.type.ZoneType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document("zones")
@CompoundIndex(name = "warehouse_zone_name_idx", def = "{'warehouseId': 1, 'name': 1}", unique = true)
public class Zone {
    @Id
    private String id;

    @Indexed
    private String warehouseId;

    private String name;

    private String description;

    @Indexed
    private ZoneType type;

    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}