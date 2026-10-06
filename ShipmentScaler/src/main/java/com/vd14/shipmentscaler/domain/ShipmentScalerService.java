package com.vd14.shipmentscaler.domain;

import com.vd14.shipmentscaler.application.port.in.ShipmentScalerUseCaseInterface;
import com.vd14.shipmentscaler.application.port.out.LoadInterfacePort;
import com.vd14.shipmentscaler.application.port.out.SaveInterfacePort;
import com.vd14.shipmentscaler.application.port.in.ShipmentValidatorInterfacePort;
import jakarta.transaction.Transactional;

import java.util.Optional;

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
    @Transactional
    public void execute(Shipment shipment) {
        // Load the stored shipment
        Optional<Shipment> loadedShipment = loadPort.loadShipments(shipment.tracking_reference());
        if (loadedShipment.isPresent()) {
            // validate the Shipment using the ShipmentValidatorInterfacePort internal method
            Shipment newShipment = validateShipment(loadedShipment.get());
            // save to database
            saveShipments(newShipment);
        } else {
            throw new ShipmentValidationException("Shipment not found with the id: "  + shipment.tracking_reference());
        }
    }

    /**
     * Private Helper method used to validates the given Shipment using the
     * ShipmentValidatorInterfacePort. If valid changes the ShipmentStatus
     * from PENDING to SHIPPED and return the new Shipment
     * @param shipment: The upcoming shipment
     * @return Shipment with the status set to SHIPPED
     */
    private Shipment validateShipment(Shipment shipment) {
        if (validatorPort.validate(shipment)) {
            return new Shipment(
                    shipment.tracking_reference(),
                    shipment.net_weight(),
                    shipment.tare_weight(),
                    shipment.gross_weight(),
                    ShipmentStatus.SHIPPED
            );
        }

        throw new ShipmentValidationException("Shipment not valid");
    }

    /**
     * Private Helper method that uses the SaveInterfacePort to save the
     * updated shipment
     * @param shipment: The given Shipment in PENDING
     * @throws ShipmentValidationException in case if failure
     */
    private void saveShipments(Shipment shipment) {
        try {
            savePort.saveShipments(shipment);
        } catch (Exception e) {
            throw new ShipmentValidationException("Shipment status update failed: " + shipment.tracking_reference());
        }
    }
}
