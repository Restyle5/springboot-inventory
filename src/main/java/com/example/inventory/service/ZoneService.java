package com.example.inventory.service;

import com.example.inventory.dto.request.zone.CreateZoneRequest;
import com.example.inventory.dto.request.zone.UpdateZoneRequest;
import com.example.inventory.dto.response.warehouse.WarehouseWithZoneResponse;
import com.example.inventory.dto.response.zone.CreateZoneResponse;
import com.example.inventory.dto.response.zone.UpdateZoneResponse;
import com.example.inventory.dto.response.zone.ZoneWithBinResponse;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.Bin;
import com.example.inventory.model.User;
import com.example.inventory.model.Warehouse;
import com.example.inventory.model.Zone;
import com.example.inventory.repository.BinRepository;
import com.example.inventory.repository.WarehouseRepository;
import com.example.inventory.repository.ZoneRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final AuthHelper authHelper;
    private final WarehouseRepository warehouseRepository;
    private final BinRepository binRepository;
    private final ZoneRepository zoneRepository;

    public CreateZoneResponse create(CreateZoneRequest request)
    {
        User currentUser = authHelper.getCurrentUser();

        // verify warehouse exists and belongs to current user's tenant
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found"));

        if (!warehouse.getTenantId().equals(currentUser.getTenantId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        Zone zone = new Zone();
        zone.setWarehouseId(request.getWarehouseId());
        zone.setName(request.getName());
        zone.setDescription(request.getDescription());
        zone.setType(request.getType());
        zone.setCreatedBy(currentUser.getId());

        Zone saved = zoneRepository.save(zone);
        return new CreateZoneResponse(
                saved.getId(),
                saved.getName(),
                saved.getType()
        );
    }

    public UpdateZoneResponse update(String zoneId, UpdateZoneRequest request)
    {
        // check for User-Tenant match Zone-warehouse identity
        authHelper.checkOwnershipByWarehouseId(request.getWarehouseId());

        Zone zone = zoneRepository.findById(zoneId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found."));


        Optional.ofNullable(request.getWarehouseId()).ifPresent(zone::setWarehouseId);
        Optional.ofNullable(request.getName()).ifPresent(zone::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(zone::setDescription);
        Optional.ofNullable(request.getType()).ifPresent(zone::setType);

        Zone saved = zoneRepository.save(zone);

        return new UpdateZoneResponse(
            saved.getId(),
            saved.getName(),
            saved.getType(),
            saved.getUpdatedAt()
        );
    }

    public ZoneWithBinResponse getBinList(String zoneId)
    {
        Zone zone = zoneRepository.findById(zoneId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found."));

        // check for User - tenant authority
        authHelper.checkOwnershipByWarehouseId(zone.getWarehouseId());

        // get zones.
        List<Bin> bins = binRepository.findByZoneId(zoneId);

        ZoneWithBinResponse response = new ZoneWithBinResponse();
        response.setId(zone.getId());
        response.setName(zone.getName());
        // map zones into warehouse.
        response.setBinSummary(bins.stream()
                .map(bin -> {
                    ZoneWithBinResponse.BinSummary summary = new ZoneWithBinResponse.BinSummary();
                    summary.setId(bin.getId());
                    summary.setZoneId(bin.getZoneId());
                    summary.setCode(bin.getCode());
                    summary.setCapacity(bin.getCapacity());
                    summary.setEmpty(bin.isEmpty());
                    summary.setCreatedAt(bin.getCreatedAt());
                    summary.setUpdatedAt(bin.getUpdatedAt());
                    return summary;
                })
                .toList());

        return response;
    }
}
