package com.vd14.shipmentscaler.application.port.out;

import com.vd14.shipmentscaler.domain.Shipment;

import java.util.Optional;

@FunctionalInterface
public interface LoadInterfacePort {
    Optional<Shipment> loadShipments();
}
