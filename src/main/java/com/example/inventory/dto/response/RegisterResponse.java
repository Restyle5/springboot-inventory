package com.example.inventory.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class RegisterResponse {
    private String username;
    private Instant createdAt;
    private String email;
}
