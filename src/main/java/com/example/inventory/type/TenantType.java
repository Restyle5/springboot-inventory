package com.example.inventory.type;

public enum TenantType {
    INDIVIDUAL("individual"),
    CORPORATE("corporate"),
    GOVERMENT("goverment");

    private final String value;

    TenantType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
