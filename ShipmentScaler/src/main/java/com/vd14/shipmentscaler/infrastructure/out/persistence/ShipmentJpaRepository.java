package com.vd14.shipmentscaler.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ShipmentJpaRepository extends JpaRepository<ShipmentJpaEntity, Long> {
    @Query("SELECT s FROM ShipmentJpaEntity s WHERE s.tracking_reference = :tracking_reference")
    Optional<ShipmentJpaEntity> findByTrackingReference(@Param("tracking_reference") UUID tracking_reference);
}
