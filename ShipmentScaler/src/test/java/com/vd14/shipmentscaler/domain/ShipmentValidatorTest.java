package com.vd14.shipmentscaler.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentValidatorTest {

    @Test
    void validateSuccessfullyValidateTheShipment() {
        // ARRANGE
        Shipment shipment = new Shipment(
                UUID.randomUUID(),
                50.0,
                4.5,
                54.5,
                ShipmentStatus.PENDING
        );

        ShipmentValidator shipmentValidator = new ShipmentValidator();
        // ACT
        boolean isValid = shipmentValidator.validate(shipment);
        // ASSERT
        assertTrue(isValid);
    }

    @Test
    void validateReturnsFalseIfTheStatusIsNotPENDING() {
        // ARRANGE
        Shipment shipment = new Shipment(
                UUID.randomUUID(),
                50.0,
                4.5,
                54.5,
                ShipmentStatus.SHIPPED
        );

        ShipmentValidator shipmentValidator = new ShipmentValidator();
        // ACT
        boolean isValid = shipmentValidator.validate(shipment);
        // ASSERT
        assertFalse(isValid);
    }
}