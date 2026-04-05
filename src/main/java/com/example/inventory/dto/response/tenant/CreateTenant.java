package com.example.inventory.dto.response.tenant;

import com.example.inventory.type.TenantType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CreateTenant {
    private String id;
    private String name;
    private TenantType type;
    private Instant createdAt;
}
