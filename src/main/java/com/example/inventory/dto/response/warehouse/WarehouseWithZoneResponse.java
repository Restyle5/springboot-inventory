package com.example.inventory.dto.response.warehouse;

import com.example.inventory.type.ZoneType;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class WarehouseWithZoneResponse {
    private String id;
    private String name;
    private List<ZoneSummary> zoneSummary;
    @Data
    public static class ZoneSummary {
        private String id;
        private String name;
        private String description;
        private ZoneType type;
        private Instant createdAt;
        private Instant updatedAt;
    }
}