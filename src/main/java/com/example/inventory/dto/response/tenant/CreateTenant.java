package com.example.inventory.dto.response.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class CreateTenant {
    private String id;
    private String name;
    private Instant createdAt;
}
