package com.example.inventory.service;

import com.example.inventory.dto.request.inventory.CreateInventoryRequest;
import com.example.inventory.dto.request.inventory.UpdateInventoryRequest;
import com.example.inventory.dto.response.inventory.CreateInventoryResponse;
import com.example.inventory.dto.response.inventory.UpdateInventoryResponse;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    private final AuthHelper authHelper;

    public CreateInventoryResponse create(CreateInventoryRequest request)
    {
        // check user's request authority
        authHelper.checkOwnershipByTenantId(request.getTenantId(), "Tenant not Found.");


        Inventory inventory = new Inventory();

        inventory.setName(request.getName());
        inventory.setCount(request.getCount());
        inventory.setPrice(request.getPrice());
        inventory.setVisible(request.isVisible());
        inventory.setTenantId(request.getTenantId());

        Inventory saved = inventoryRepository.save(inventory);

        return new CreateInventoryResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getName(),
                saved.getCount(),
                saved.getPrice(),
                saved.isVisible(),
                saved.getCreatedAt()

        );
    }

    public UpdateInventoryResponse update(String id, UpdateInventoryRequest request){

        Inventory inventory = inventoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found."));

        // check user's request authority
        authHelper.checkOwnershipByTenantId(inventory.getTenantId(), "Inventory not found.");

        Optional.ofNullable(request.getName()).ifPresent(inventory::setName);
        Optional.ofNullable(request.getPrice()).ifPresent(inventory::setPrice);
        Optional.ofNullable(request.getCount()).ifPresent(inventory::setCount);
        Optional.ofNullable(request.getVisible()).ifPresent(inventory::setVisible);

        Inventory saved = inventoryRepository.save(inventory);

        return new UpdateInventoryResponse(
                saved.getId(),
                saved.getTenantId(),
                saved.getName(),
                saved.getCount(),
                saved.getPrice(),
                saved.isVisible(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );

    }
}
