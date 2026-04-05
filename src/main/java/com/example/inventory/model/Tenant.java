package com.example.inventory.model;

import com.example.inventory.type.TenantType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document("tenants")
public class Tenant {
    @Id
    private String id;

    // keep it simple for now, a user manage multiple tenant (1:M)
    @Indexed
    private String managedBy;

    @Indexed(unique = true)
    private String name;

    @Indexed
    private TenantType type;

    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
