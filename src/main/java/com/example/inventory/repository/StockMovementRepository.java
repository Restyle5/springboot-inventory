package com.example.inventory.repository;

import com.example.inventory.model.StockMovement;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface StockMovementRepository extends MongoRepository<StockMovement, String> {
    List<StockMovement> findByStockUnitId(String stockUnitId);
}
