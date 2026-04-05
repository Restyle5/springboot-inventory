package com.example.inventory.repository;

import com.example.inventory.dto.response.tenant.ListTenant;
import com.example.inventory.model.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TenantRepository extends MongoRepository<Tenant, String> {
    List<ListTenant> findByCreatedBy(String createdBy);
}
