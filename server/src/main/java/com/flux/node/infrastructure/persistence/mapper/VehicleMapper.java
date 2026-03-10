package com.flux.node.infrastructure.persistence.mapper;

import com.flux.node.domain.model.Vehicle;
import com.flux.node.infrastructure.persistence.entity.VehicleEntity;

public class VehicleMapper {
    public static Vehicle toDomain(VehicleEntity entity) {
        return new Vehicle(
            entity.getId(),
            entity.getLicensePlate(),
            entity.getModel(),
            entity.getDriver() != null ? entity.getDriver().getId() : null
        );
    }

    public static VehicleEntity toEntity(Vehicle domain) {
        VehicleEntity entity = new VehicleEntity();
        entity.setId(domain.getId());
        entity.setLicensePlate(domain.getLicensePlate());
        entity.setModel(domain.getModel());
        return entity;
    }
}
