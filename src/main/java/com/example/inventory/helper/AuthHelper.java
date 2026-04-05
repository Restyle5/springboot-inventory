package com.example.inventory.helper;

import com.example.inventory.model.Tenant;
import com.example.inventory.model.User;
import com.example.inventory.model.Warehouse;
import com.example.inventory.repository.TenantRepository;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthHelper {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final WarehouseRepository warehouseRepository;

    /**
     * @return User instance.
     */
    public User getCurrentUser() {
        Jwt jwt = (Jwt) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        String keycloakId = jwt.getSubject();

        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }
    /**
     * @param tenantId Model: User has tenantId;
     */
    public void checkOwnership(String tenantId)
    {
        User currentUser = getCurrentUser();
        if(!tenantId.equals(currentUser.getTenantId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found.");
        }
    }
    public void isTenantExist(String id) {
         tenantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found"));
    }
    public Tenant getTenant(String tenantId){
        return tenantRepository.findById(tenantId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found"));
    }

    public void checkOwnershipByWarehouseId(String warehouseId){
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found"));
        // check for User - tenant authority
        checkOwnership(warehouse.getTenantId());

    }

}