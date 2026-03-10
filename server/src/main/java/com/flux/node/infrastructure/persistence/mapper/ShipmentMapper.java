package com.flux.node.infrastructure.persistence.mapper;

import com.flux.node.domain.model.Shipment;
import com.flux.node.infrastructure.persistence.entity.ShipmentEntity;
import com.flux.node.infrastructure.persistence.entity.VehicleEntity;

public class ShipmentMapper {
    public static Shipment toDomain(ShipmentEntity entity) {
        return new Shipment(
            entity.getId(),
            entity.getName(),
            entity.getOriginAddress(),
            entity.getOriginLat(),
            entity.getOriginLng(),
            entity.getMinTemp(),
            entity.getMaxTemp(),
            entity.getTemperature(),
            entity.getDestinationAddress(),
            entity.getDestinationLat(),
            entity.getDestinationLng(),
            entity.getVehicles().stream().map(VehicleEntity::getId).toList(),
            entity.getStatus(),
            entity.getEstimatedPickupTime(),
            entity.getEstimatedDeliveryTime(),
            entity.getPriority()
        );
    }

    public static ShipmentEntity toEntity(Shipment domain) {
        ShipmentEntity entity = new ShipmentEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setOriginAddress(domain.getOriginAddress());
        entity.setOriginLat(domain.getOriginLat());
        entity.setOriginLng(domain.getOriginLng());
        entity.setTemperature(domain.getTemperature());
        entity.setDestinationAddress(domain.getDestinationAddress());
        entity.setDestinationLat(domain.getDestinationLat());
        entity.setDestinationLng(domain.getDestinationLng());
        entity.setStatus(domain.getStatus());
        entity.setMinTemp(domain.getMinTemp());
        entity.setMaxTemp(domain.getMaxTemp());
        entity.setEstimatedDeliveryTime(domain.getEstimatedDeliveryTime());
        entity.setEstimatedPickupTime(domain.getEstimatedPickupTime());
        entity.setPriority(domain.getPriority());
        return entity;
    }
}
