package com.flux.node.application.usecase.shipment;

import java.util.List;

import com.flux.node.application.command.shipment.ShipmentCommand;
import com.flux.node.domain.model.Shipment;
import com.flux.node.domain.repository.ShipmentRepository;

public class ShipmentUseCase {
    
    private final ShipmentRepository repo;

    public ShipmentUseCase(ShipmentRepository repo) {
        this.repo = repo;
    }

    public void createShipment(ShipmentCommand command) {
        Shipment shipment = new Shipment(
            command.name(),
            command.originAddress(),
            command.originLat(),
            command.originLng(),
            command.minTemp(),
            command.maxTemp(),
            command.temperature(),
            command.destinationAddress(),
            command.destinationLat(),
            command.destinationLng(),
            command.vehicleIds(),
            command.estimatedPickupTime(),
            command.estimatedDeliveryTime(),
            command.priority()
        );
        repo.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return repo.findAll();
    }
}
