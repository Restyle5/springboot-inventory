package com.example.inventory.service;

import com.example.inventory.dto.request.warehouse.CreateWarehouseRequest;
import com.example.inventory.dto.request.warehouse.UpdateWarehouseRequest;
import com.example.inventory.dto.response.tenant.TenantWithUsersResponse;
import com.example.inventory.dto.response.warehouse.CreateWarehouseResponse;
import com.example.inventory.dto.response.warehouse.UpdateWarehouseResponse;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.Tenant;
import com.example.inventory.model.User;
import com.example.inventory.model.Warehouse;
import com.example.inventory.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final AuthHelper authHelper;

    public CreateWarehouseResponse create(CreateWarehouseRequest request) {
        User currentUser = authHelper.getCurrentUser();

        Warehouse warehouse = new Warehouse();
        warehouse.setTenantId(currentUser.getTenantId());
        warehouse.setName(request.getName());
        warehouse.setAddress(request.getAddress());
        warehouse.setCreatedBy(currentUser.getId());

        Warehouse saved = warehouseRepository.save(warehouse);
        return new CreateWarehouseResponse(
            saved.getId(),
            saved.getName(),
            saved.getAddress(),
            saved.getCreatedAt()
        );
    }

    public UpdateWarehouseResponse update(String warehouseId, UpdateWarehouseRequest request)
    {

        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found"));

        // check for User - tenant authority
        authHelper.checkOwnership(warehouse.getTenantId());

        Optional.ofNullable(request.getName()).ifPresent(warehouse::setName);
        Optional.ofNullable(request.getAddress()).ifPresent(warehouse::setAddress);

        Warehouse saved = warehouseRepository.save(warehouse);

        return new UpdateWarehouseResponse(
                saved.getId(),
                saved.getName(),
                saved.getAddress(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}
