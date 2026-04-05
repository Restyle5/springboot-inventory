package com.example.inventory.dto.request.zone;

import com.example.inventory.type.ZoneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateZoneRequest {

    @NotBlank(message = "Warehouse id is required")
    private String warehouseId;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Zone type is required")
    private ZoneType type;
}