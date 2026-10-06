package com.vd14.shipmentscaler.domain;

import java.util.UUID;

public record Shipment (
    UUID tracking_reference,
    Double net_weight,
    Double tare_weight,
    Double gross_weight,
    ShipmentStatus status
) { }
