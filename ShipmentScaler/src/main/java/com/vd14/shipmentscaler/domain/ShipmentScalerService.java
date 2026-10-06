package com.vd14.shipmentscaler.domain;

import com.vd14.shipmentscaler.application.port.in.ShipmentScalerUseCaseInterface;
import com.vd14.shipmentscaler.application.port.out.LoadInterfacePort;
import com.vd14.shipmentscaler.application.port.out.SaveInterfacePort;
import com.vd14.shipmentscaler.application.port.out.ShipmentValidatorInterfacePort;

public class ShipmentScalerService implements ShipmentScalerUseCaseInterface {
    private final LoadInterfacePort loadPort;
    private final SaveInterfacePort savePort;
    private final ShipmentValidatorInterfacePort validatorPort;

    public ShipmentScalerService(
            LoadInterfacePort loadPort,
            SaveInterfacePort savePort,
            ShipmentValidatorInterfacePort validatorPort
    ) {
        this.loadPort = loadPort;
        this.savePort = savePort;
        this.validatorPort = validatorPort;
    }

    @Override
    public void execute(Shipment shipment) {

    }
}
