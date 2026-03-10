package com.flux.node.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.flux.node.domain.enums.ShipmentPriority;
import com.flux.node.domain.enums.ShipmentStatus;

public class Shipment {
   private Long id;
   private String name;
   private String originAddress;
   private Double originLat;
   private Double originLng;
   private Double minTemp;
   private Double maxTemp;
   private Double temperature;
    
   private String destinationAddress;
   private Double destinationLat;
   private Double destinationLng;

   private List<Long> vehicleIds;

   private ShipmentStatus status;

   private LocalDateTime estimatedPickupTime;
   private LocalDateTime estimatedDeliveryTime;
   private ShipmentPriority priority;

   public Shipment(String name, String originAddress, Double originLat, Double originLng, Double minTemp, double maxTemp, Double temperature,
                   String destinationAddress, Double destinationLat, Double destinationLng,
                   List<Long> vehicleIds, LocalDateTime estimatedPickupTime, LocalDateTime estimatedDeliveryTime, ShipmentPriority priority) {
      this.name = name;
      this.originAddress = originAddress;
      this.originLat = originLat;
      this.originLng = originLng;
      this.minTemp = minTemp;
      this.maxTemp = maxTemp;
      this.temperature = temperature;
      this.destinationAddress = destinationAddress;
      this.destinationLat = destinationLat;
      this.destinationLng = destinationLng;
      this.vehicleIds = vehicleIds;
      this.status = ShipmentStatus.PENDING; 
      this.estimatedPickupTime = estimatedPickupTime;
      this.estimatedDeliveryTime = estimatedDeliveryTime;
      this.priority = priority;
   }

   public Shipment(Long id, String name, String originAddress, Double originLat, Double originLng, Double minTemp, Double maxTemp, Double temperature,
                   String destinationAddress, Double destinationLat, Double destinationLng,
                   List<Long> vehicleIds, ShipmentStatus status,  LocalDateTime estimatedPickupTime, LocalDateTime estimatedDeliveryTime, ShipmentPriority priority) {
      this.id = id;
      this.name = name;
      this.originAddress = originAddress;
      this.originLat = originLat;
      this.originLng = originLng;
      this.minTemp = minTemp;
      this.maxTemp = maxTemp;
      this.temperature = temperature;
      this.destinationAddress = destinationAddress;
      this.destinationLat = destinationLat;
      this.destinationLng = destinationLng;
      this.vehicleIds = vehicleIds;
      this.status = status;
      this.estimatedPickupTime = estimatedPickupTime;
      this.estimatedDeliveryTime = estimatedDeliveryTime;
      this.priority = priority;
   }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public Double getOriginLat() {
        return originLat;
    }

    public Double getOriginLng() {
        return originLng;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public Double getDestinationLat() {
        return destinationLat;
    }

    public Double getDestinationLng() {
        return destinationLng;
    }

    public List<Long> getVehicleIds() {
        return vehicleIds;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public Double getMinTemp() {
        return minTemp;
    }
    public Double getMaxTemp() {
        return maxTemp;
    }

    public LocalDateTime getEstimatedPickupTime() {
        return estimatedPickupTime;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public ShipmentPriority getPriority() {
        return priority;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

}
