package com.example.inventory.service;

import com.example.inventory.dto.request.RegisterTenantRequest;
import com.example.inventory.dto.request.UpdateTenantRequest;
import com.example.inventory.dto.response.tenant.CreateTenant;
import com.example.inventory.dto.response.tenant.ListTenant;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.Tenant;
import com.example.inventory.model.User;
import com.example.inventory.repository.TenantRepository;
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

        authHelper.checkOwnership(tenant.getCreatedBy());

        Optional.ofNullable(request.getName()).ifPresent(tenant::setName);

        return tenantRepository.save(tenant);
    }

    public List<ListTenant> getList()
    {
        User currentUser = authHelper.getCurrentUser();
        return tenantRepository.findByCreatedBy(currentUser.getId());
    }

}
