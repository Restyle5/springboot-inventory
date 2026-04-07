package com.example.inventory.service;

import com.example.inventory.dto.request.bin.CreateBinRequest;
import com.example.inventory.dto.request.bin.UpdateBinRequest;
import com.example.inventory.dto.response.bin.BinWithStockUnitResponse;
import com.example.inventory.dto.response.bin.CreateBinResponse;
import com.example.inventory.dto.response.bin.UpdateBinResponse;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.*;
import com.example.inventory.repository.BinRepository;
import com.example.inventory.repository.StockUnitRepository;
import com.example.inventory.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BinService {
    private final AuthHelper authHelper;
    private final ZoneRepository zoneRepository;
    private final BinRepository binRepository;
    private final StockUnitRepository stockUnitRepository;

    public CreateBinResponse create(CreateBinRequest request)
    {
        User currentUser = authHelper.getCurrentUser();
        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found."));

        // check for user-warehouse authority
        authHelper.checkOwnershipByWarehouseId(zone.getWarehouseId());

        Bin bin = new Bin();
        bin.setZoneId(request.getZoneId());
        bin.setZoneType(zone.getType());
        bin.setTenantId(currentUser.getTenantId());
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

        if (request.getZoneId() != null) {
            Zone zone = zoneRepository.findById(request.getZoneId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found."));

            authHelper.checkOwnershipByWarehouseId(zone.getWarehouseId());

            bin.setZoneId(zone.getId());
            bin.setZoneType(zone.getType());

        }else {
            authHelper.checkOwnership(bin.getTenantId());
        }

        Optional.ofNullable(request.getCode()).ifPresent(bin::setCode);
        Optional.ofNullable(request.getCapacity()).ifPresent(bin::setCapacity);

        Bin saved = binRepository.save(bin);

        return new UpdateBinResponse(
                saved.getId(),
                saved.getZoneId(),
                saved.getZoneType(),
                saved.getCode(),
                saved.getCapacity(),
                saved.getUpdatedAt()

        );
    }

    public BinWithStockUnitResponse getStockUnitList(String Id)
    {
        Bin bin = binRepository.findById(Id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bin not found."));
        authHelper.checkOwnership(bin.getTenantId());

        List<StockUnit> stockUnitList = stockUnitRepository.findByBinId(bin.getId());
        BinWithStockUnitResponse binWithStockUnitResponse = new BinWithStockUnitResponse();

        binWithStockUnitResponse.setId(bin.getId());
        binWithStockUnitResponse.setCode(bin.getCode());
        binWithStockUnitResponse.setCapacity(bin.getCapacity());
        binWithStockUnitResponse.setZoneId(bin.getZoneId());
        binWithStockUnitResponse.setZoneType(bin.getZoneType());
        binWithStockUnitResponse.setEmpty(bin.isEmpty());
        binWithStockUnitResponse.setStockUnits(stockUnitList.stream().map(
                stockunit -> {
                    BinWithStockUnitResponse.StockUnitSummary stockUnitSummary = new BinWithStockUnitResponse.StockUnitSummary();
                    stockUnitSummary.setId(stockunit.getId());
                    stockUnitSummary.setProductId(stockunit.getProductId());
                    stockUnitSummary.setStatus(stockunit.getStatus());
                    stockUnitSummary.setBatchNumber(stockunit.getBatchNumber());
                    stockUnitSummary.setSerialNumber(stockunit.getSerialNumber());
                    stockUnitSummary.setExpiryDate(stockunit.getExpiryDate());
                    stockUnitSummary.setUpdatedAt(stockunit.getUpdatedAt());
                    return stockUnitSummary;
                }
        ).toList());

        return binWithStockUnitResponse;

    }
}
