package com.vd14.shipmentscaler.domain;

import com.vd14.shipmentscaler.application.port.out.LoadInterfacePort;
import com.vd14.shipmentscaler.application.port.out.SaveInterfacePort;
import lombok.Getter;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentScalerServiceTest {

    @Test
    void executeSuccessfullyUpdateTheStateOfTheShipmentScaler() {
        // ARRANGE
        Shipment shipment = makeShipment(ShipmentStatus.PENDING);
        MockLoadInterfacePort loadPort = new MockLoadInterfacePort(true);
        MockSaveInterfacePort savePort = new MockSaveInterfacePort(true);
        // Testing the ShipmentValidator in integration with the ShipmentScalerService
        ShipmentValidator shipmentValidator = new ShipmentValidator();
        // SUT or System Under Test
        ShipmentScalerService sut = new ShipmentScalerService(loadPort, savePort, shipmentValidator);
        // ACT
        sut.execute(shipment);
        // ASSERT & VERIFY
        assertAll(
                () -> assertTrue(loadPort.isVerifyCall()),
                () -> assertTrue(savePort.isVerifyCall())
        );
    }

    /**
     * Private Helper used to mock the LoadInterfacePort type
     */
    private static class MockLoadInterfacePort implements LoadInterfacePort {
        private final boolean shouldSucceed;
        @Getter
        private boolean verifyCall;

        public MockLoadInterfacePort(boolean shouldSucceed) {
            this.shouldSucceed = shouldSucceed;
        }

        @Override
        public Optional<Shipment> loadShipments(UUID tracking_reference) {
            if (shouldSucceed) {
                verifyCall = true;
                return Optional.of(makeShipment(ShipmentStatus.PENDING));
            }
            return Optional.empty();
        }
    }

    /**
     * Private Helper used to mock the SaveInterfacePort type
     */
    private static class MockSaveInterfacePort implements SaveInterfacePort {
        private final boolean shouldSucceed;
        @Getter
        private boolean verifyCall;

        public MockSaveInterfacePort(boolean shouldSucceed) {
            this.shouldSucceed = shouldSucceed;
        }

        @Override
        public void saveShipments(Shipment shipment) {
            verifyCall = true;
            if (!shouldSucceed) throw new ShipmentTransactionException("Should not succeed");
        }
    }

    private static @NonNull Shipment makeShipment(ShipmentStatus currentStatus) {
        return new Shipment(
                UUID.randomUUID(),
                50.0,
                4.5,
                54.5,
                currentStatus
        );
    }
}