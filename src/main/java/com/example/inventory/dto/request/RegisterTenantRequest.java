package com.example.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterTenantRequest {

    @NotBlank(message = "Name is required.")
    private String name;
}