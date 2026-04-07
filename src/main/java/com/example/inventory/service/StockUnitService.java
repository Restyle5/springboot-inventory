package com.example.inventory.service;

import com.example.inventory.dto.request.stockunit.CreateStockUnitRequest;
import com.example.inventory.dto.request.stockunit.UpdateStockUnitRequest;
import com.example.inventory.dto.response.stockunit.CreateStockUnitResponse;
import com.example.inventory.dto.response.stockunit.StockUnitWithStockMovementResponse;
import com.example.inventory.dto.response.stockunit.UpdateStockUnitResponse;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.*;
import com.example.inventory.repository.BinRepository;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.StockMovementRepository;
import com.example.inventory.repository.StockUnitRepository;
import com.example.inventory.type.StockUnitStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockUnitService {

    private final StockUnitRepository stockUnitRepository;
    private final ProductRepository productRepository;
    private final BinRepository binRepository;
    private final StockMovementRepository stockMovementRepository;
    private final AuthHelper authHelper;

    public CreateStockUnitResponse create(CreateStockUnitRequest request)
    {
        User currentUser = authHelper.getCurrentUser();


        // Verify user's tenant matches product's tenant.
        Product product = productRepository.findById(request.getProductId())
                .filter(p -> currentUser.getTenantId().equals(p.getTenantId()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found."
                ));

        Bin bin = binRepository.findById(request.getBinId())
                .filter(b -> currentUser.getTenantId().equals(b.getTenantId()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bin not found."
                ));

        // check bin capacity
        long currentCount = stockUnitRepository.countByBinId(request.getBinId());
        if (currentCount >= bin.getCapacity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bin is at full capacity");
        }

        // check product requiredZoneType matches bin's zone type*
        if (!bin.getZoneType().equals(product.getRequiredZoneType())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product zone type does not match bin zone type");
        }

        StockUnit stockUnit = new StockUnit();
        stockUnit.setProductId(request.getProductId());
        stockUnit.setBinId(request.getBinId());
        stockUnit.setSerialNumber(request.getSerialNumber());
        stockUnit.setBatchNumber(request.getBatchNumber());
        stockUnit.setExpiryDate(request.getExpiryDate());
        stockUnit.setStatus(StockUnitStatus.AVAILABLE);
        stockUnit.setCreatedBy(currentUser.getId());

        StockUnit saved = stockUnitRepository.save(stockUnit);

        // update bin isEmpty flag
        bin.setEmpty(false);
        binRepository.save(bin);

        return new CreateStockUnitResponse(
                saved.getId(),
                saved.getProductId(),
                saved.getStatus(),
                saved.getBinId(),
                saved.getSerialNumber(),
                saved.getBatchNumber(),
                saved.getExpiryDate(),
                saved.getCreatedAt()
        );
    }

    public UpdateStockUnitResponse update(String stockUnitId, UpdateStockUnitRequest request)
    {
        User currentUser = authHelper.getCurrentUser();


        StockUnit stockUnit = stockUnitRepository.findById(stockUnitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock unit not found"));

        // verify ownership
        Product product = productRepository.findById(stockUnit.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if (!product.getTenantId().equals(currentUser.getTenantId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        // if moving to a different bin
        if (request.getBinId() != null) {
            Bin newBin = binRepository.findById(request.getBinId())
                    .filter(b -> currentUser.getTenantId().equals(b.getTenantId()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bin not found"));

            // check new bin capacity
            long currentCount = stockUnitRepository.countByBinId(request.getBinId());
            if (currentCount >= newBin.getCapacity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bin is at full capacity");
            }

            // check zone type compatibility
            if (!newBin.getZoneType().equals(product.getRequiredZoneType())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product zone type does not match bin zone type");
            }

            // update old bin isEmpty
            Bin oldBin = binRepository.findById(stockUnit.getBinId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Old bin not found"));
            long oldBinCount = stockUnitRepository.countByBinId(stockUnit.getBinId());
            if (oldBinCount <= 1) {
                oldBin.setEmpty(true);
                binRepository.save(oldBin);
            }

            // update new bin isEmpty
            newBin.setEmpty(false);
            binRepository.save(newBin);

            stockUnit.setBinId(request.getBinId());
        }

        Optional.ofNullable(request.getStatus()).ifPresent(stockUnit::setStatus);
        Optional.ofNullable(request.getExpiryDate()).ifPresent(stockUnit::setExpiryDate);

        StockUnit saved = stockUnitRepository.save(stockUnit);

        return new UpdateStockUnitResponse(
                saved.getId(),
                saved.getProductId(),
                saved.getStatus(),
                saved.getBinId(),
                saved.getSerialNumber(),
                saved.getBatchNumber(),
                saved.getExpiryDate(),
                saved.getUpdatedAt()
        );
    }

    public StockUnitWithStockMovementResponse getStockMovementList(String Id)
    {
        User currentUser = authHelper.getCurrentUser();

        StockUnit stockUnit = stockUnitRepository.findById(Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock unit not found"));

        List<StockMovement> stockMovements = stockMovementRepository.findByStockUnitId(stockUnit.getId());

        // verify ownership
        Product product = productRepository.findById(stockUnit.getProductId())
                .filter(p -> currentUser.getTenantId().equals(p.getTenantId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));


        StockUnitWithStockMovementResponse stockUnitWithStockMovementResponse = new StockUnitWithStockMovementResponse();
        stockUnitWithStockMovementResponse.setId(stockUnit.getId());
        stockUnitWithStockMovementResponse.setProductId(stockUnit.getProductId());
        stockUnitWithStockMovementResponse.setBinId(stockUnit.getBinId());
        stockUnitWithStockMovementResponse.setSerialNumber(stockUnit.getSerialNumber());
        stockUnitWithStockMovementResponse.setStatus(stockUnit.getStatus());
        stockUnitWithStockMovementResponse.setUpdatedAt(stockUnit.getUpdatedAt());
        stockUnitWithStockMovementResponse.setStockMovements(
                stockMovements.stream().map(
                        stockMovement -> {
                            StockUnitWithStockMovementResponse.StockMovementSummary StockMovementSummary = new StockUnitWithStockMovementResponse.StockMovementSummary();
                            StockMovementSummary.setId(stockMovement.getId());
                            StockMovementSummary.setMovementType(stockMovement.getType());
                            StockMovementSummary.setFromBinId(stockMovement.getFromBinId());
                            StockMovementSummary.setToBinId(stockMovement.getToBinId());
                            StockMovementSummary.setPerformedBy(stockMovement.getPerformedBy());
                            StockMovementSummary.setNote(stockMovement.getNote());
                            StockMovementSummary.setCreatedAt(stockMovement.getCreatedAt());

                            return StockMovementSummary;
                        }
                ).toList()
        );
        return stockUnitWithStockMovementResponse;
    }

}
