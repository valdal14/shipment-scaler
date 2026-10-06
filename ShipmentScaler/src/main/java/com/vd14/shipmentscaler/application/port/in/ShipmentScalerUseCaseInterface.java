package com.vd14.shipmentscaler.application.port.in;

import com.vd14.shipmentscaler.domain.Shipment;

@FunctionalInterface
public interface ShipmentScalerUseCaseInterface {
    void execute(Shipment shipment);
}
