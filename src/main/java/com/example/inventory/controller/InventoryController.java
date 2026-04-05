package com.example.inventory.controller;

import com.example.inventory.dto.request.inventory.UpdateInventoryRequest;
import com.example.inventory.dto.response.inventory.CreateInventoryResponse;
import com.example.inventory.dto.response.inventory.UpdateInventoryResponse;
import com.example.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

    @PostMapping("/create")
    public ResponseEntity<CreateInventoryResponse> create(
            @Valid @RequestBody com.example.inventory.dto.request.inventory.CreateInventoryRequest request
            )
    {
        return new ResponseEntity<>(inventoryService.create(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateInventoryResponse> update(@PathVariable String id, @RequestBody UpdateInventoryRequest request)
    {
        return new ResponseEntity<>(inventoryService.update(id, request), HttpStatus.OK);
    }
}
