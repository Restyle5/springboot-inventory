package com.example.inventory.dto.response.tenant;

import lombok.Data;

import java.time.Instant;

@Data
public class ListTenant {
    private String id;
    private String name;
    private String createdBy;
    private Instant createdAt;
}