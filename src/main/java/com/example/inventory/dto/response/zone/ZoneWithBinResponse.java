package com.example.inventory.dto.response.zone;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ZoneWithBinResponse {
    private String id;
    private String name;
    private List<BinSummary> binSummary;
    @Data
    public static class BinSummary {
        private String id;
        private String zoneId;
        private String code;
        private Integer capacity;
        private boolean isEmpty;
        private Instant createdAt;
        private Instant updatedAt;
    }
}