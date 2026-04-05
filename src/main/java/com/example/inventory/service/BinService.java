package com.example.inventory.service;

import com.example.inventory.dto.request.bin.CreateBinRequest;
import com.example.inventory.dto.request.bin.UpdateBinRequest;
import com.example.inventory.dto.response.bin.CreateBinResponse;
import com.example.inventory.dto.response.bin.UpdateBinResponse;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.Bin;
import com.example.inventory.model.User;
import com.example.inventory.model.Zone;
import com.example.inventory.repository.BinRepository;
import com.example.inventory.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BinService {
    private final AuthHelper authHelper;
    private final ZoneRepository zoneRepository;
    private final BinRepository binRepository;

    public CreateBinResponse create(CreateBinRequest request)
    {
        User currentUser = authHelper.getCurrentUser();
        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found."));

        // check for user-warehouse authority
        authHelper.checkOwnershipByWarehouseId(zone.getWarehouseId());

        Bin bin = new Bin();
        bin.setZoneId(request.getZoneId());
        bin.setCode(request.getCode());
        bin.setCapacity(request.getCapacity());
        bin.setCreatedBy(currentUser.getId());

        Bin saved = binRepository.save(bin);
        return new CreateBinResponse(
                saved.getId(),
                saved.getZoneId(),
                saved.getCode()
        );
    }

    public UpdateBinResponse update(String binId, UpdateBinRequest request)
    {
        Bin bin = binRepository.findById(binId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bin not found."));

        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found."));

        // check for User-Tenant match Zone-warehouse identity
        authHelper.checkOwnershipByWarehouseId(zone.getWarehouseId());


        Optional.ofNullable(request.getZoneId()).ifPresent(bin::setZoneId);
        Optional.ofNullable(request.getCode()).ifPresent(bin::setCode);
        Optional.ofNullable(request.getCapacity()).ifPresent(bin::setCapacity);

        Bin saved = binRepository.save(bin);

        return new UpdateBinResponse(
                saved.getId(),
                saved.getZoneId(),
                saved.getCode(),
                saved.getCapacity(),
                saved.getUpdatedAt()

        );
    }
}
