package com.example.inventory.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document("inventories")
// Unique combination of tenant and names. Different tenant inventory can have similar name to the other.
@CompoundIndex(name = "tenant_name_idx", def = "{'tenantId': 1, 'name': 1}", unique = true)
@Data
public class Inventory {
    @Id
    private String id;

    @Indexed
    private String tenantId;

    private String name;

    private Integer count;

    private BigDecimal price;

    private String currency;

    private boolean visible = true; // There's one more default, check in dto/request/inventory.

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;


}
