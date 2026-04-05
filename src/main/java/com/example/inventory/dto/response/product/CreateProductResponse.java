package com.example.inventory.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProductResponse {
    private String id;
    private String name;
}
