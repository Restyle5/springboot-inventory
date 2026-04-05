package com.example.inventory.controller;

import com.example.inventory.dto.request.RegisterTenantRequest;
import com.example.inventory.dto.request.UpdateTenantRequest;
import com.example.inventory.dto.response.tenant.CreateTenant;
import com.example.inventory.dto.response.tenant.TenantWithProductResponse;
import com.example.inventory.dto.response.tenant.TenantWithUsersResponse;
import com.example.inventory.dto.response.warehouse.TenantWithWarehouseResponse;
import com.example.inventory.model.Tenant;
import com.example.inventory.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
@RequiredArgsConstructor
public class TenantController {

    @Autowired
    private final TenantService tenantService;

    @PostMapping("/create")
    public ResponseEntity<CreateTenant> create(
            @Valid @RequestBody RegisterTenantRequest request
            ){
        return new ResponseEntity<>(tenantService.create(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tenant> update(@PathVariable String id, @RequestBody UpdateTenantRequest request){
        return new ResponseEntity<>(tenantService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<TenantWithUsersResponse> getUserList()
    {
        return new ResponseEntity<>(tenantService.getUserList(), HttpStatus.OK);
    }

    @GetMapping("/warehouses")
    public ResponseEntity<TenantWithWarehouseResponse> getWarehouseList()
    {
        return new ResponseEntity<>(tenantService.getWarehouseList(), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<TenantWithProductResponse> getProductList()
    {
        return new ResponseEntity<>(tenantService.getProductList(), HttpStatus.OK);
    }
}
