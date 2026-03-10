package com.flux.node.domain.model;

public class Vehicle {
    private Long id;
    private String licensePlate;
    private String model;
    private Long driverId;

    public Vehicle(String licensePlate, String model, Long driverId) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.driverId = driverId;
    }

    public Vehicle(Long id, String licensePlate, String model, Long driverId) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.model = model;
        this.driverId = driverId;
    }

    public Long getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public Long getDriverId() {
        return driverId;
    }
}
