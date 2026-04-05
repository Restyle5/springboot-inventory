package com.example.inventory.service;

import com.example.inventory.dto.request.RegisterTenantRequest;
import com.example.inventory.dto.request.UpdateTenantRequest;
import com.example.inventory.dto.response.tenant.CreateTenant;
import com.example.inventory.dto.response.tenant.TenantWithProductResponse;
import com.example.inventory.dto.response.tenant.TenantWithUsersResponse;
import com.example.inventory.dto.response.warehouse.TenantWithWarehouseResponse;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.Product;
import com.example.inventory.model.Tenant;
import com.example.inventory.model.User;
import com.example.inventory.model.Warehouse;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.TenantRepository;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final AuthHelper authHelper;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    /**
     * @param request Type RegisterTenantRequest
     * @return return a value of something
     */
    public CreateTenant create(RegisterTenantRequest request)
    {
            User currentUser = authHelper.getCurrentUser();
            Tenant tenant = new Tenant();

            // tenant Information
            tenant.setName(request.getName());
            tenant.setCreatedBy(currentUser.getId());

            Tenant saved = tenantRepository.save(tenant);

            return new CreateTenant(
                    saved.getId(),
                    saved.getName(),
                    saved.getCreatedAt()
            );
        }

    public Tenant update(String id, UpdateTenantRequest request)
    {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found"));

        // check for User - tenant authority
        authHelper.checkOwnership(id);

        Optional.ofNullable(request.getName()).ifPresent(tenant::setName);

        return tenantRepository.save(tenant);
    }

    public TenantWithUsersResponse getUserList()
    {
        User currentUser = authHelper.getCurrentUser();

        Tenant tenant = tenantRepository.findById(currentUser.getTenantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found"));

        List<User> users = userRepository.findByTenantId(currentUser.getTenantId());

        TenantWithUsersResponse response = new TenantWithUsersResponse();
        response.setId(tenant.getId());
        response.setName(tenant.getName());
        response.setCreatedAt(tenant.getCreatedAt());
        response.setUsers(users.stream()
                .map(user -> {
                    TenantWithUsersResponse.UserSummary summary = new TenantWithUsersResponse.UserSummary();
                    summary.setId(user.getId());
                    summary.setFirstname(user.getFirstName());
                    summary.setLastname(user.getLastName());
                    summary.setEmail(user.getEmail());
                    summary.setCreatedAt(user.getCreatedAt());
                    return summary;
                })
                .toList());

        return response;
    }

    public TenantWithWarehouseResponse getWarehouseList()
    {
        User currentUser = authHelper.getCurrentUser();
        Tenant tenant = tenantRepository.findById(currentUser.getTenantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found"));

        List<Warehouse> warehouses = warehouseRepository.findByTenantId(currentUser.getTenantId());

        TenantWithWarehouseResponse response = new TenantWithWarehouseResponse();
        response.setId(tenant.getId());
        response.setName(tenant.getName());
        response.setCreatedAt(tenant.getCreatedAt());
        response.setWarehouses(warehouses.stream()
                .map(warehouse -> {
                    TenantWithWarehouseResponse.WarehouseSummary summary = new TenantWithWarehouseResponse.WarehouseSummary();
                    summary.setId(warehouse.getId());
                    summary.setName(warehouse.getName());
                    summary.setAddress(warehouse.getAddress());
                    summary.setCreatedBy(warehouse.getCreatedBy());
                    summary.setCreatedAt(warehouse.getCreatedAt());
                    summary.setUpdatedAt(warehouse.getUpdatedAt());
                    return summary;
                })
                .toList());

        return response;
    }

    public TenantWithProductResponse getProductList()
    {
        User currentUser = authHelper.getCurrentUser();
        Tenant tenant = tenantRepository.findById(currentUser.getTenantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found"));

        List<Product> products = productRepository.findByTenantId(currentUser.getTenantId());

        TenantWithProductResponse response = new TenantWithProductResponse();
        response.setId(tenant.getId());
        response.setName(tenant.getName());
        response.setCreatedAt(tenant.getCreatedAt());
        response.setProducts(products.stream()
                .map(product -> {
                    TenantWithProductResponse.ProductSummary summary = new TenantWithProductResponse.ProductSummary();
                    summary.setId(product.getId());
                    summary.setName(product.getName());
                    summary.setSku(product.getSku());
                    summary.setDescription(product.getDescription());
                    summary.setCategory(product.getCategory());
                    summary.setRequiredZoneType(product.getRequiredZoneType());
                    return summary;
                })
                .toList());

        return response;
    }
}
