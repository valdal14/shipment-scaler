package com.vd14.shipmentscaler.application.port.out;

import com.vd14.shipmentscaler.domain.Shipment;

@FunctionalInterface
public interface SaveInterfacePort {
    Shipment saveShipments(Shipment shipment);
}
