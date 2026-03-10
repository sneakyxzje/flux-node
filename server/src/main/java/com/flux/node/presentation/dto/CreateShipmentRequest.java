package com.flux.node.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.flux.node.domain.enums.ShipmentPriority;

public record CreateShipmentRequest(
    String name,
    String originAddress,
    Double originLat,
    Double originLng,
    Double minTemp,
    Double maxTemp,
    Double temperature,
    String destinationAddress,
    Double destinationLat,
    Double destinationLng,
    LocalDateTime estimatedPickupTime,
    LocalDateTime estimatedDeliveryTime,
    ShipmentPriority priority,

    List<Long> vehicleIds
) {
    
}
