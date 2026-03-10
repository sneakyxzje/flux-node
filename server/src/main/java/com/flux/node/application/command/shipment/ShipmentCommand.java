package com.flux.node.application.command.shipment;

import java.time.LocalDateTime;
import java.util.List;

import com.flux.node.domain.enums.ShipmentPriority;

public record ShipmentCommand(
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
