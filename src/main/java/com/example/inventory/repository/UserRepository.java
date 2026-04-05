package com.example.inventory.repository;

import com.example.inventory.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByKeycloakId(String keycloakId);
    Optional<User> findByEmail(String email);
    List<User> findByTenantId(String tenantId);
    boolean existsByEmail(String email);
}
