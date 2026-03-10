package com.flux.node.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.flux.node.domain.enums.ShipmentPriority;
import com.flux.node.domain.enums.ShipmentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shipments")
@Getter
@Setter
public class ShipmentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String originAddress;
    private Double originLat;
    private Double originLng;
    private Double minTemp;
    private Double maxTemp;
    private LocalDateTime estimatedPickupTime;
    private LocalDateTime estimatedDeliveryTime;
    private Double temperature;
    private String destinationAddress;
    private Double destinationLat;
    private Double destinationLng;
    
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
    
    @Enumerated(EnumType.STRING)
    private ShipmentPriority priority;
    @ManyToMany
    @JoinTable(name = "shipment_vehicles", joinColumns = @JoinColumn(name = "shipment_id"),
        inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<VehicleEntity> vehicles;
}
