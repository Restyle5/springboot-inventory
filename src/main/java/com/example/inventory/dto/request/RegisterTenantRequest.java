package com.example.inventory.dto.request;

import com.example.inventory.type.TenantType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterTenantRequest {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull(message = "Tenant Type is required.")
    private TenantType tenantType;
}