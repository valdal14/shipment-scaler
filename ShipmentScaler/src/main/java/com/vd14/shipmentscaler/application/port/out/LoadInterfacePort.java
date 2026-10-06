package com.vd14.shipmentscaler.application.port.out;

import com.vd14.shipmentscaler.domain.Shipment;

import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface LoadInterfacePort {
    Optional<Shipment> loadShipments(UUID tracking_reference);
}
