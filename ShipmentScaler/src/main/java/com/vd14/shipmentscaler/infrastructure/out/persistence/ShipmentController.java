package com.vd14.shipmentscaler.infrastructure.out.persistence;

import com.vd14.shipmentscaler.application.port.in.ShipmentScalerUseCaseInterface;
import com.vd14.shipmentscaler.domain.Shipment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentController {

    private final ShipmentScalerUseCaseInterface shipmentScalerUseCase;

    public ShipmentController(ShipmentScalerUseCaseInterface shipmentScalerUseCase) {
        this.shipmentScalerUseCase = shipmentScalerUseCase;
    }

    @PostMapping("/approve-and-ship")
    public ResponseEntity<Void> processShipment(@RequestBody Shipment shipment) {
        shipmentScalerUseCase.execute(shipment);
        return ResponseEntity.ok().build();
    }
}
