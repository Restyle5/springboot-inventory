package com.example.inventory.repository;

import com.example.inventory.model.Zone;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ZoneRepository extends MongoRepository<Zone, String> {
    List<Zone> findByWarehouseId(String warehouseId);
}
