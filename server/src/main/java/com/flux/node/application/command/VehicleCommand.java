package com.flux.node.application.command;

public record VehicleCommand(
    String licensePlate,
    String model,
    Long driverId
) {
    
}
