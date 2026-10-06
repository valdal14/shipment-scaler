package com.vd14.shipmentscaler.infrastructure.out.persistence;

import com.vd14.shipmentscaler.application.port.out.LoadInterfacePort;
import com.vd14.shipmentscaler.application.port.out.SaveInterfacePort;
import com.vd14.shipmentscaler.domain.Shipment;
import com.vd14.shipmentscaler.domain.ShipmentStatus;
import com.vd14.shipmentscaler.domain.ShipmentTransactionException;

import java.util.Optional;
import java.util.UUID;

public class ShippingScalerAdapter implements LoadInterfacePort, SaveInterfacePort {
    private final ShipmentJpaRepository shipmentJpaRepository;

    public ShippingScalerAdapter(ShipmentJpaRepository shipmentJpaRepository) {
        this.shipmentJpaRepository = shipmentJpaRepository;
    }

    @Override
    public Optional<Shipment> loadShipments(UUID tracking_reference) {
        Optional<ShipmentJpaEntity> entity = shipmentJpaRepository.findByTrackingReference(tracking_reference);
        if (entity.isPresent()) {
            UUID tf = entity.get().getTracking_reference();
            Double net_weight = entity.get().getNet_weight();
            Double tare_weight = entity.get().getTare_weight();
            Double gross_weight = entity.get().getGross_weight();
            ShipmentStatus status = entity.get().getStatus();

            return Optional.of(new Shipment(tf, net_weight, tare_weight, gross_weight, status));
        }
        return Optional.empty();
    }

    @Override
    public void saveShipments(Shipment shipment) throws Exception {
        // Fetch the existing entity from the database using the tracking reference
        Optional<ShipmentJpaEntity> existingEntityOpt = shipmentJpaRepository.findByTrackingReference(shipment.tracking_reference());

        if (existingEntityOpt.isPresent()) {
            // If it exists, update the state of the existing entity.
            ShipmentJpaEntity existingEntity = existingEntityOpt.get();
            existingEntity.setNet_weight(shipment.net_weight());
            existingEntity.setTare_weight(shipment.tare_weight());
            existingEntity.setGross_weight(shipment.gross_weight());
            existingEntity.setStatus(shipment.status());

            shipmentJpaRepository.save(existingEntity);
        } else {
            throw new ShipmentTransactionException("Shipment not found for id: " + shipment.tracking_reference());
        }
    }
}
