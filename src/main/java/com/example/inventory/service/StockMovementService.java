package com.example.inventory.service;

import com.example.inventory.dto.request.stockmovement.CreateStockMovementRequest;
import com.example.inventory.dto.response.stockmovement.CreateStockMovementResponse;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.*;
import com.example.inventory.repository.BinRepository;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.repository.StockMovementRepository;
import com.example.inventory.repository.StockUnitRepository;
import com.example.inventory.type.MovementType;
import com.example.inventory.type.StockUnitStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor


public class StockMovementService {

    private final AuthHelper authHelper;
    private final StockUnitRepository stockUnitRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;
    private final BinRepository binRepository;

    public CreateStockMovementResponse create(CreateStockMovementRequest request) {

        User currentUser = authHelper.getCurrentUser();

        // verify stock unit exists and belongs to tenant
        StockUnit stockUnit = stockUnitRepository.findById(request.getStockUnitId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock unit not found"));

        Product product = productRepository.findById(stockUnit.getProductId())
                .filter(p -> currentUser.getTenantId().equals(p.getTenantId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // validate based on movement type
        switch (request.getType()) {
            case STOCK_IN -> {
                if (request.getToBinId() == null)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "toBinId is required for STOCK_IN");
            }
            case STOCK_OUT -> {
                if (request.getFromBinId() == null)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromBinId is required for STOCK_OUT");
            }
            case TRANSFER -> {
                if (request.getFromBinId() == null || request.getToBinId() == null)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromBinId and toBinId are required for TRANSFER");
            }
        }

        // handle fromBin — STOCK_OUT or TRANSFER
        if (request.getType() == MovementType.STOCK_OUT || request.getType() == MovementType.TRANSFER) {
            Bin fromBin = binRepository.findById(request.getFromBinId())
                    .filter(b -> currentUser.getTenantId().equals(b.getTenantId()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "From bin not found"));

            long remainingCount = stockUnitRepository.countByBinId(request.getFromBinId());
            if (remainingCount <= 1) {
                fromBin.setEmpty(true);
                binRepository.save(fromBin);
            }
        }

        // handle toBin — STOCK_IN or TRANSFER
        if (request.getType() == MovementType.STOCK_IN || request.getType() == MovementType.TRANSFER) {
            Bin toBin = binRepository.findById(request.getToBinId())
                    .filter(b -> currentUser.getTenantId().equals(b.getTenantId()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "To bin not found"));

            // check capacity
            long currentCount = stockUnitRepository.countByBinId(request.getToBinId());
            if (currentCount >= toBin.getCapacity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bin is at full capacity");
            }

            // check zone type compatibility
            if (!toBin.getZoneType().equals(product.getRequiredZoneType())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product zone type does not match bin zone type");
            }

            toBin.setEmpty(false);
            binRepository.save(toBin);
        }

        // update stock unit based on movement type
        switch (request.getType()) {
            case STOCK_OUT -> {
                stockUnit.setStatus(StockUnitStatus.SHIPPED);
                stockUnit.setBinId(null);
            }
            case TRANSFER -> {
                stockUnit.setBinId(request.getToBinId());
            }
            case QUARANTINE -> {
                stockUnit.setStatus(StockUnitStatus.QUARANTINE);
            }
            case RETURN, ADJUSTMENT -> {
                stockUnit.setStatus(StockUnitStatus.AVAILABLE);
            }
        }
        stockUnitRepository.save(stockUnit);

        // save movement record
        StockMovement movement = new StockMovement();
        movement.setStockUnitId(request.getStockUnitId());
        movement.setType(request.getType());
        movement.setFromBinId(request.getFromBinId());
        movement.setToBinId(request.getToBinId());
        movement.setPerformedBy(currentUser.getId());
        movement.setNote(request.getNote());

        StockMovement saved = stockMovementRepository.save(movement);
        return new CreateStockMovementResponse(
                saved.getId(),
                saved.getStockUnitId(),
                saved.getType(),
                saved.getFromBinId(),
                saved.getToBinId(),
                saved.getNote(),
                saved.getCreatedAt()
        );
    }
}