package com.example.inventory.repository;

import com.example.inventory.model.StockUnit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockUnitRepository extends MongoRepository<StockUnit, String> {
    long countByBinId(String binId);
}
