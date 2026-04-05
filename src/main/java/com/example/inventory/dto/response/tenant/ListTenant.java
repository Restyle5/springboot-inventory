package com.example.inventory.dto.response.tenant;

import com.example.inventory.type.TenantType;
import lombok.Data;

import java.time.Instant;

@Data
public class ListTenant {
    private String id;
    private String name;
    private TenantType type;
    private String createdBy;
    private Instant createdAt;
}