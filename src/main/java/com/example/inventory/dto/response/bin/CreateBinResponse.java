package com.example.inventory.dto.response.bin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBinResponse {
    private String id;
    private String zoneId;
    private String code;
}


