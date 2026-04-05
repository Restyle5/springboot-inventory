package com.example.inventory.dto.response.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UpdateWarehouseResponse {
    private String id;
    private String name;
    private String address;
    private Instant createdAt;
    private Instant updatedAt;
}
