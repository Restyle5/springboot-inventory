package com.example.inventory.controller;

import com.example.inventory.dto.request.stockmovement.CreateStockMovementRequest;
import com.example.inventory.dto.response.stockmovement.CreateStockMovementResponse;
import com.example.inventory.service.StockMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stockmovement")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping("/create")
    public ResponseEntity<CreateStockMovementResponse> create(
            @Valid @RequestBody CreateStockMovementRequest request
            )
    {
        return new ResponseEntity<>(stockMovementService.create(request), HttpStatus.CREATED);
    }
}
