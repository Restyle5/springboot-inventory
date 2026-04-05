package com.example.inventory.dto.response.zone;

import com.example.inventory.type.ZoneType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateZoneResponse {
    private String id;
    private String name;
    private ZoneType type;
}
