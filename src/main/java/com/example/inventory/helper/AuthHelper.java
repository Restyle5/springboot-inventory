package com.example.inventory.helper;

import com.example.inventory.model.Tenant;
import com.example.inventory.model.User;
import com.example.inventory.repository.TenantRepository;
import com.example.inventory.repository.UserRepository;
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


    /**
     * @return User instance.
     */
    public User getCurrentUser() {
        Jwt jwt = (Jwt) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        assert jwt != null;
        String keycloakId = jwt.getSubject();

        return userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    /**
     * @param managedBy Model: Tenant (managedBy), referring to Model: User (id).
     */
    public void checkOwnership(String managedBy)
    {
        checkOwnership(managedBy,"Tenant Not found.");
    }
    public void checkOwnership(String managedBy, String ErrMsg){
        User currentUser = getCurrentUser();
        if(!managedBy.equals(currentUser.getId()))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrMsg);
        }
    }
    public Tenant getTenant(String id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tenant not found"));
    }
    /**
     * Cases When it's not necessary to have tenants called outside authService.
     * @param id TenantId
     * @param ErrMsg e.g. Inventory not found.
     */
    public void checkOwnershipByTenantId(String id, String ErrMsg){
        Tenant tenant  = getTenant(id);
        checkOwnership(tenant.getCreatedBy(), ErrMsg);
    }
}