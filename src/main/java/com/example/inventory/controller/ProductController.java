package com.example.inventory.controller;

import com.example.inventory.dto.request.product.CreateProductRequest;
import com.example.inventory.dto.request.product.UpdateProductRequest;
import com.example.inventory.dto.response.product.CreateProductResponse;
import com.example.inventory.dto.response.product.UpdateProductResponse;
import com.example.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<CreateProductResponse> create(
            @Valid @RequestBody CreateProductRequest request
            )
    {
        return new ResponseEntity<>(productService.create(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateProductResponse> update(
            @PathVariable  String id,
            @Valid @RequestBody UpdateProductRequest request
            )
    {
        return new ResponseEntity<>(productService.update(id, request), HttpStatus.OK);
    }
}
