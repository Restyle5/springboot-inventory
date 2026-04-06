package com.example.inventory.dto.response.bin;

import com.example.inventory.type.ZoneType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UpdateBinResponse {
    private String id;
    private String zoneId;
    private ZoneType zoneType;
    private String code;
    private Integer capacity;
    private Instant updatedAt;
}
