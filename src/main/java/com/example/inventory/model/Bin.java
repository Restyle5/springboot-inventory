package com.example.inventory.model;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document("bins")
@CompoundIndex(name = "zone_bin_code_idx", def = "{'zoneId': 1, 'code': 1}", unique = true)
public class Bin {
    @Id
    private String id;

    @Indexed
    private String zoneId;

    private String code;

    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    private boolean isEmpty = true;

    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}