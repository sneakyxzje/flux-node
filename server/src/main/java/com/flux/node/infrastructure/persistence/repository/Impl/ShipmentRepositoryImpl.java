package com.flux.node.infrastructure.persistence.repository.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flux.node.domain.model.Shipment;
import com.flux.node.domain.repository.ShipmentRepository;
import com.flux.node.infrastructure.persistence.entity.ShipmentEntity;
import com.flux.node.infrastructure.persistence.entity.VehicleEntity;
import com.flux.node.infrastructure.persistence.mapper.ShipmentMapper;
import com.flux.node.infrastructure.persistence.repository.JPA.JPAShipmentRepository;
import com.flux.node.infrastructure.persistence.repository.JPA.JPAVehicleRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShipmentRepositoryImpl implements ShipmentRepository{
    private final JPAShipmentRepository jpaRepo;
    private final JPAVehicleRepository jpaVehRepo;
    @Override
    public Shipment save(Shipment shipment) {
        ShipmentEntity entity = ShipmentMapper.toEntity(shipment);
        List<VehicleEntity> vehicles = jpaVehRepo.findAllById(shipment.getVehicleIds());
        entity.setVehicles(vehicles);
        ShipmentEntity saved = jpaRepo.save(entity);

        return ShipmentMapper.toDomain(saved);
    }

    @Override
    public List<Shipment> findAll() {
        List<ShipmentEntity> entities = jpaRepo.findAll();
        return entities.stream().map(ShipmentMapper::toDomain).toList();
    }
}
