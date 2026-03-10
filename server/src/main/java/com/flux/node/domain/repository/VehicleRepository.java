package com.flux.node.domain.repository;

import java.util.List;
import java.util.Optional;

import com.flux.node.domain.model.Vehicle;

public interface VehicleRepository {
    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(Long id);
    List<Vehicle> findAllById(List<Long> ids);
}
