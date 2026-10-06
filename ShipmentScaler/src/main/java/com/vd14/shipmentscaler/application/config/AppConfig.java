package com.vd14.shipmentscaler.application.config;

import com.vd14.shipmentscaler.application.port.in.ShipmentValidatorInterfacePort;
import com.vd14.shipmentscaler.application.port.out.LoadInterfacePort;
import com.vd14.shipmentscaler.application.port.out.SaveInterfacePort;
import com.vd14.shipmentscaler.domain.ShipmentScalerService;
import com.vd14.shipmentscaler.domain.ShipmentValidator;
import com.vd14.shipmentscaler.infrastructure.out.persistence.ShipmentJpaRepository;
import com.vd14.shipmentscaler.infrastructure.out.persistence.ShippingScalerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ShipmentValidator shipmentValidator() {
        return new ShipmentValidator();
    }

    @Bean
    public ShipmentScalerService shipmentScalerService(LoadInterfacePort loadPort, SaveInterfacePort savePort, ShipmentValidatorInterfacePort  validatorPort) {
        return new ShipmentScalerService(loadPort, savePort, validatorPort);
    }

    @Bean
    public ShippingScalerAdapter shippingScalerAdapter(ShipmentJpaRepository repo) {
        return new ShippingScalerAdapter(repo);
    }
}
