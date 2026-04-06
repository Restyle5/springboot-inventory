package com.example.inventory.repository;

import com.example.inventory.model.StockMovement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockMovementRepository extends MongoRepository<StockMovement, String> {
}
