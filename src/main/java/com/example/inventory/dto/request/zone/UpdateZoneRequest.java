package com.example.inventory.dto.request.zone;

import com.example.inventory.type.ZoneType;
import lombok.Data;

@Data
public class UpdateZoneRequest {

    private String warehouseId;
    private String name;
    private String description;
    private ZoneType type;
}