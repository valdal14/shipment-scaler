package com.vd14.shipmentscaler.domain;

import com.vd14.shipmentscaler.application.port.out.ShipmentValidatorInterfacePort;

public class ShipmentValidator implements ShipmentValidatorInterfacePort {
    @Override
    public boolean validate(Shipment shipment) {
        double net = shipment.net_weight();
        double gross = shipment.gross_weight();
        double tare = shipment.tare_weight();
        String tracking = shipment.tracking_reference().toString();
        ShipmentStatus shipmentStatus = shipment.status();

        // validation of the shipment
        if (gross != (net + tare)) return false;
        if (net != (gross - tare)) return false;
        if (tare != (gross - net)) return false;
        if (shipmentStatus != ShipmentStatus.PENDING) return false;
        return !tracking.isEmpty();
    }
}
