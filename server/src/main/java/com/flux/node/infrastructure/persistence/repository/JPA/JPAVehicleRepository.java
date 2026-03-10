package com.flux.node.infrastructure.persistence.repository.JPA;


import org.springframework.data.jpa.repository.JpaRepository;

import com.flux.node.infrastructure.persistence.entity.VehicleEntity;

public interface JPAVehicleRepository extends JpaRepository<VehicleEntity, Long> {
    
}
