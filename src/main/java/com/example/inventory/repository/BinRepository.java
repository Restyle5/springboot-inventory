package com.example.inventory.repository;

import com.example.inventory.model.Bin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BinRepository extends MongoRepository<Bin, String> {
    List<Bin> findByZoneId(String zoneId);
}
