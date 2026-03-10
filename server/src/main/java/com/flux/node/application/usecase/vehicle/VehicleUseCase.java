package com.flux.node.application.usecase.vehicle;

import com.flux.node.application.command.VehicleCommand;
import com.flux.node.domain.model.Vehicle;
import com.flux.node.domain.repository.VehicleRepository;

public class VehicleUseCase {
    private final VehicleRepository repo;

    public VehicleUseCase(VehicleRepository repo) {
        this.repo = repo;
    }

    public void createVehicle(VehicleCommand command) {
        Vehicle vehicle = new Vehicle(
        command.licensePlate(), command.model(), command.driverId());
        repo.save(vehicle);
    }
}
