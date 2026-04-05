package com.example.inventory.repository;

import com.example.inventory.model.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
    List<Warehouse> findByTenantId(String tenantId);
}
