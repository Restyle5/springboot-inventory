package com.example.inventory.controller;

import com.example.inventory.dto.request.warehouse.CreateWarehouseRequest;
import com.example.inventory.dto.request.warehouse.UpdateWarehouseRequest;
import com.example.inventory.dto.response.warehouse.CreateWarehouseResponse;
import com.example.inventory.dto.response.warehouse.UpdateWarehouseResponse;
import com.example.inventory.dto.response.warehouse.WarehouseWithZoneResponse;
import com.example.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    @Autowired
    private final WarehouseService warehouseService;

    @PostMapping("/create")
    public ResponseEntity<CreateWarehouseResponse> create (
            @Valid @RequestBody CreateWarehouseRequest request
            )
    {
        return new ResponseEntity<>(warehouseService.create(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateWarehouseResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateWarehouseRequest request
            ){
        return new ResponseEntity<>(warehouseService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/{id}/zones")
    public ResponseEntity<WarehouseWithZoneResponse> getZoneList(
            @PathVariable String id
    )
    {
        return new ResponseEntity<>(warehouseService.getZoneList(id), HttpStatus.OK);
    }
}
