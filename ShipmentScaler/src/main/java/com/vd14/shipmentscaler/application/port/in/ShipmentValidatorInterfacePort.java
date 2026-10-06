package com.vd14.shipmentscaler.application.port.in;

import com.vd14.shipmentscaler.domain.Shipment;

@FunctionalInterface
public interface ShipmentValidatorInterfacePort {
    boolean validate(Shipment shipment);
}
