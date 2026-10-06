package com.vd14.shipmentscaler.domain;

public class ShipmentValidationException extends RuntimeException {
    public ShipmentValidationException(String message) {
        super(message);
    }
}
