package com.example.inventory.dto.response.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class CreateWarehouseResponse {
     private String id;
     private String name;
     private String address;
     private Instant createdAt;
}