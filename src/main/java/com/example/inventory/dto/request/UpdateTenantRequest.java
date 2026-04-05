package com.example.inventory.dto.request;

import com.example.inventory.type.TenantType;
import lombok.Data;

@Data
public class UpdateTenantRequest
{
    private String name;
    private TenantType tenantType;
}
