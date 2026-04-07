package com.example.inventory.type;

public enum MovementType {
    STOCK_IN,        // received into warehouse
    STOCK_OUT,       // shipped out of warehouse
    TRANSFER,        // moved from one bin to another
    ADJUSTMENT,      // manual correction
    QUARANTINE,      // flagged for inspection
    RETURN           // returned from shipment
}