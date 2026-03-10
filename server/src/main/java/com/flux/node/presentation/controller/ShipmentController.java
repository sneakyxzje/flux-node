package com.flux.node.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flux.node.application.command.shipment.ShipmentCommand;
import com.flux.node.application.usecase.shipment.ShipmentUseCase;
import com.flux.node.presentation.dto.CreateShipmentRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
public class ShipmentController {
    
    private final ShipmentUseCase shipmentUseCase;
    @PostMapping
    public ResponseEntity<?> createShipment(@RequestBody CreateShipmentRequest request) {
        ShipmentCommand command = new ShipmentCommand(
            request.name(),
            request.originAddress(),
            request.originLat(),
            request.originLng(),
            request.minTemp(),
            request.maxTemp(),
            request.temperature(),
            request.destinationAddress(),
            request.destinationLat(),
            request.destinationLng(),
            request.estimatedPickupTime(),
            request.estimatedDeliveryTime(),
            request.priority(),
            request.vehicleIds()
        );
        shipmentUseCase.createShipment(command);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<?> getAllShipments() {
        return ResponseEntity.ok(shipmentUseCase.getAllShipments());
    }
}
