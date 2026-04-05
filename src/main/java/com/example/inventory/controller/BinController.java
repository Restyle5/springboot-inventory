package com.example.inventory.controller;

import com.example.inventory.dto.request.bin.CreateBinRequest;
import com.example.inventory.dto.request.bin.UpdateBinRequest;
import com.example.inventory.dto.response.bin.CreateBinResponse;
import com.example.inventory.dto.response.bin.UpdateBinResponse;
import com.example.inventory.service.BinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bin")
public class BinController {

    private final BinService binService;

    @PostMapping("/create")
    public ResponseEntity<CreateBinResponse> create(
            @Valid @RequestBody CreateBinRequest request
    )
    {
        return new ResponseEntity<>(binService.create(request), HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateBinResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateBinRequest request
            )
    {
        return new ResponseEntity<>(binService.update(id, request), HttpStatus.OK);
    }
}
