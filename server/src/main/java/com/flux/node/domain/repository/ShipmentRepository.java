package com.flux.node.domain.repository;

import java.util.List;

import com.flux.node.domain.model.Shipment;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    List<Shipment> findAll();
}
