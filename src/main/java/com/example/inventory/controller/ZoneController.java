package com.example.inventory.controller;


import com.example.inventory.dto.request.zone.CreateZoneRequest;
import com.example.inventory.dto.request.zone.UpdateZoneRequest;
import com.example.inventory.dto.response.zone.CreateZoneResponse;
import com.example.inventory.dto.response.zone.UpdateZoneResponse;
import com.example.inventory.dto.response.zone.ZoneWithBinResponse;
import com.example.inventory.service.ZoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/zone")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping("/create")
    public ResponseEntity<CreateZoneResponse> create(
            @Valid @RequestBody CreateZoneRequest request
            )
    {
        return new ResponseEntity<>(zoneService.create(request), HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateZoneResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateZoneRequest request
    )
    {
        return new ResponseEntity<>(zoneService.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/{id}/bins")
    public ResponseEntity<ZoneWithBinResponse> getBinList(
            @PathVariable String id
    )
    {
        return new ResponseEntity<>(zoneService.getBinList(id), HttpStatus.OK);
    }
}
