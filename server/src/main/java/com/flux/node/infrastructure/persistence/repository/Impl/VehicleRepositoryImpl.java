package com.flux.node.infrastructure.persistence.repository.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.flux.node.domain.model.Vehicle;
import com.flux.node.domain.repository.VehicleRepository;
import com.flux.node.infrastructure.persistence.entity.UserEntity;
import com.flux.node.infrastructure.persistence.entity.VehicleEntity;
import com.flux.node.infrastructure.persistence.mapper.VehicleMapper;
import com.flux.node.infrastructure.persistence.repository.JPA.JPAVehicleRepository;
import com.flux.node.infrastructure.persistence.repository.JPA.user.JPAUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VehicleRepositoryImpl implements VehicleRepository {
    private final JPAVehicleRepository jpaRepo;
    private final JPAUserRepository jpaUserRepo;
    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleEntity entity = VehicleMapper.toEntity(vehicle);
        if(vehicle.getDriverId() != null) {
            UserEntity driver = jpaUserRepo.findById(vehicle.getDriverId())
            .orElseThrow(() -> new IllegalArgumentException("Driver not found"));
            entity.setDriver(driver);
        }
        VehicleEntity saved = jpaRepo.save(entity);
        return VehicleMapper.toDomain(saved);
    } 

    @Override
    public Optional<Vehicle> findById(Long id) {
        Optional<VehicleEntity> entity = jpaRepo.findById(id);
        return entity.map(VehicleMapper::toDomain);
    }

    @Override
    public List<Vehicle> findAllById(List<Long> ids) {
        List<VehicleEntity> entities = jpaRepo.findAllById(ids);
        return entities.stream().map(VehicleMapper::toDomain).toList();
    }
}
