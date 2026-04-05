package com.example.inventory.dto.response.zone;

import com.example.inventory.type.ZoneType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UpdateZoneResponse {
    private String id;
    private String name;
    private ZoneType type;
    private Instant updatedAt;
}
