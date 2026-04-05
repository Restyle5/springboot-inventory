package com.example.inventory.dto.response.tenant;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class TenantWithUsersResponse {
    private String id;
    private String name;
    private Instant createdAt;
    private List<UserSummary> users;

    @Data
    public static class UserSummary {
        private String id;
        private String firstname;
        private String lastname;
        private String email;
        private Instant createdAt;
    }
}