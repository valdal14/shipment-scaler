package com.vd14.shipmentscaler.infrastructure.out.persistence;

import com.vd14.shipmentscaler.domain.ShipmentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Table(name = "shipments")
@Entity
public class ShipmentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID tracking_reference;
    private Double net_weight;
    private Double tare_weight;
    private Double gross_weight;
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;
}