package com.example.inventory.service;

import com.example.inventory.dto.request.product.CreateProductRequest;
import com.example.inventory.dto.request.product.UpdateProductRequest;
import com.example.inventory.dto.response.bin.CreateBinResponse;
import com.example.inventory.dto.response.product.CreateProductResponse;
import com.example.inventory.dto.response.product.UpdateProductResponse;
import com.example.inventory.helper.AuthHelper;
import com.example.inventory.model.Product;
import com.example.inventory.model.User;
import com.example.inventory.model.Zone;
import com.example.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final AuthHelper authHelper;
    private final ProductRepository productRepository;
    public CreateProductResponse create(CreateProductRequest request)
    {
        User  user = authHelper.getCurrentUser();

        Product product = new Product();
        product.setTenantId(user.getTenantId());
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setRequiredZoneType(request.getRequiredZoneType());
        product.setWeight(request.getWeight());
        product.setLength(request.getLength());
        product.setWidth(request.getWidth());
        product.setHeight(request.getHeight());
        product.setDimensionUnit(request.getDimensionUnit());
        product.setWeightUnit(request.getWeightUnit());

        Product saved = productRepository.save(product);
        return new CreateProductResponse(
                saved.getId(),
                saved.getName()
        );
    }

    public UpdateProductResponse update(String id, UpdateProductRequest request)
    {
        User user = authHelper.getCurrentUser();
        Product product = productRepository.findById(id)
                .filter(p -> user.getTenantId().equals(p.getTenantId()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product not found."
                ));

        Optional.ofNullable(request.getName()).ifPresent(product::setName);
        Optional.ofNullable(request.getSku()).ifPresent(product::setSku);
        Optional.ofNullable(request.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(request.getCategory()).ifPresent(product::setCategory);
        Optional.ofNullable(request.getRequiredZoneType()).ifPresent(product::setRequiredZoneType);

        Optional.ofNullable(request.getWeight()).ifPresent(product::setWeight);
        Optional.ofNullable(request.getLength()).ifPresent(product::setLength);
        Optional.ofNullable(request.getWidth()).ifPresent(product::setWidth);
        Optional.ofNullable(request.getHeight()).ifPresent(product::setHeight);

        Optional.ofNullable(request.getDimensionUnit()).ifPresent(product::setDimensionUnit);
        Optional.ofNullable(request.getWeightUnit()).ifPresent(product::setWeightUnit);

        Product saved = productRepository.save(product);

        return new UpdateProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getSku(),
                saved.getDescription(),
                saved.getCategory(),
                saved.getRequiredZoneType(),
                saved.getWeight(),
                saved.getLength(),
                saved.getWidth(),
                saved.getHeight(),
                saved.getDimensionUnit(),
                saved.getWeightUnit(),
                saved.getUpdatedAt()
        );
    }
}
