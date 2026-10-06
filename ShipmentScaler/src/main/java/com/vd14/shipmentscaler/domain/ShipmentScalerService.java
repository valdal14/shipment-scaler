package com.vd14.shipmentscaler.domain;

import com.vd14.shipmentscaler.application.port.in.ShipmentScalerUseCaseInterface;
import com.vd14.shipmentscaler.application.port.out.LoadInterfacePort;
import com.vd14.shipmentscaler.application.port.out.SaveInterfacePort;

public class ShipmentScalerService implements ShipmentScalerUseCaseInterface {
    private final LoadInterfacePort loadPort;
    private final SaveInterfacePort savePort;

    public ShipmentScalerService(LoadInterfacePort loadPort, SaveInterfacePort savePort) {
        this.loadPort = loadPort;
        this.savePort = savePort;
    }

    @Override
    public void execute(Shipment shipment) {

    }
}
