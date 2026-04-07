package com.example.inventory.controller;

import com.example.inventory.dto.request.stockunit.CreateStockUnitRequest;
import com.example.inventory.dto.request.stockunit.UpdateStockUnitRequest;
import com.example.inventory.dto.response.stockunit.CreateStockUnitResponse;
import com.example.inventory.dto.response.stockunit.StockUnitWithStockMovementResponse;
import com.example.inventory.dto.response.stockunit.UpdateStockUnitResponse;
import com.example.inventory.service.StockUnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stockunit")
@RequiredArgsConstructor
public class StockUnitController {

    private final StockUnitService stockUnitService;

    @PostMapping("/create")
    public ResponseEntity<CreateStockUnitResponse> create(
            @Valid @RequestBody CreateStockUnitRequest request
            )
    {
        return new ResponseEntity<>(stockUnitService.create(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateStockUnitResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateStockUnitRequest request
            )
    {
        return new ResponseEntity<>(stockUnitService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/{id}/stockmovements")
    public ResponseEntity<StockUnitWithStockMovementResponse> getStockMovementList(
            @PathVariable String id
    )
    {
        return new ResponseEntity<>(stockUnitService.getStockMovementList(id), HttpStatus.OK);
    }
}
