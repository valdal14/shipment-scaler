# ShipmentScaler

ShipmentScaler is a micro-application designed to calculate shipping weights (Net, Gross, and Tare). Its primary purpose is not the calculations themselves, but serving as a concrete, easily understood domain to demonstrate the five fundamental tiers of software testing within a strict Hexagonal Architecture (Ports and Adapters).

## Testing Tiers Demonstrated

### 1. Unit Testing
**Purpose:** To verify that individual, isolated components work perfectly on their own without external dependencies. 
**Approach:** Test-Driven Development (TDD). We write a failing test first, then implement the pure business logic to make it pass.

**The Test:**
```java
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
```

Initial Result: because the initial method simply returned false

```Bash
org.opentest4j.AssertionFailedError: 
Expected :true 
Actual :false
```

**The Implementation:**

```Java
public class ShipmentValidator implements ShipmentValidatorInterfacePort {  
    @Override  
    public boolean validate(Shipment shipment) {  
        double net = shipment.net_weight();  
        double gross = shipment.gross_weight();  
        double tare = shipment.tare_weight();  
        String tracking = shipment.tracking_reference().toString();  
        ShipmentStatus shipmentStatus = shipment.status();  
  
        // Core domain validation rules
        if (gross != (net + tare)) return false;  
        if (net != (gross - tare)) return false;  
        if (tare != (gross - net)) return false;  
        if (shipmentStatus != ShipmentStatus.PENDING) return false;  
        
        return !tracking.isEmpty();  
    }  
}
```

### 2. Integration Testing
Purpose: To verify that separate modules (like domain services and infrastructure ports) communicate correctly when wired together.
Approach: We test the ShipmentScalerService (System Under Test) integrating with the real ShipmentValidator, while using mocked infrastructure ports (MockLoadInterfacePort, MockSaveInterfacePort) to simulate the database.

**The Test:**

```Java
@Test  
void executeSuccessfullyUpdateTheStateOfTheShipmentScaler() {  
    // ARRANGE  
    Shipment shipment = makeShipment(ShipmentStatus.PENDING);  
    MockLoadInterfacePort loadPort = new MockLoadInterfacePort(true);  
    MockSaveInterfacePort savePort = new MockSaveInterfacePort(true);  
    ShipmentValidator shipmentValidator = new ShipmentValidator();  
    
    // SUT
    ShipmentScalerService sut = new ShipmentScalerService(loadPort, savePort, shipmentValidator);  
    
    // ACT  
    sut.execute(shipment);  
    
    // ASSERT & VERIFY  
    assertAll(  
            () -> assertTrue(loadPort.isVerifyCall()),  
            () -> assertTrue(savePort.isVerifyCall())  
    );  
}
```

Initial Result: AssertionFailedError (because the execute method was empty).

```Bash
org.opentest4j.AssertionFailedError: 
Expected :true
Actual   :false
```

**The Implementation:**

```Java
@Override  
public void execute(Shipment shipment) {  
    // Load the stored shipment  
    Optional<Shipment> loadedShipment = loadPort.loadShipments(shipment.tracking_reference());  
    
    if (loadedShipment.isPresent()) {  
        // Validate the Shipment using the internal method  
        Shipment newShipment = validateShipment(loadedShipment.get());  
        // Save to database  
        saveShipments(newShipment);  
    } else {  
        throw new ShipmentValidationException("Shipment not found with the id: "  + shipment.tracking_reference());  
    }  
}
```

### 3. System Testing

Purpose: To evaluate the completely integrated application from end to end (Web Controller -> Application Service -> Database).
Approach: Instead of running Java assertions, we spin up the Spring Boot context and hit the actual REST API, verifying that it correctly locates the record in the H2 database, updates the status, and saves it.

When the application start accessing to the H2 console we can see the current record with the `PENDING` state


Execution:
Send an HTTP POST request to the running application at http://localhost:8080/api/v1/shipments/approve-and-ship with the following payload:

```JSON
{
    "tracking_reference": "26e259e6-4dc1-4cb7-813e-d8ae407ac868",
    "net_weight": 50.0,
    "tare_weight": 4.5,
    "gross_weight": 54.5,
    "status": "SHIPPED"
}
```

Result: The system returns a 200 OK, successfully proving the inbound web adapter, the core use case, and the outbound JPA persistence adapter all work together to update the row. Now the new state is `SHIPPED` as expected. 

### 4. Regression Testing
Purpose: To confirm that recent code changes or new features have not broken existing, working functionality.
Approach in this Project: Regression testing is not a separate testing framework or a new set of distinct tests. Rather, it is the act of re-running the entire suite of Unit, Integration, and System tests automatically. For example, if we were to add a new feature to support converting Kilograms to Pounds, we would run our test phase to prove that our original metric calculations still behave exactly as expected.

### 5. Acceptance Testing
Purpose: To validate the software against high-level business requirements and confirm it meets the end-user's expectations.
Approach in this Project: While Unit and Integration tests prove the code is built right, Acceptance testing proves we built the right code. For this exercise, it is assumed that the overarching business requirement—rejecting mathematically invalid weights and successfully transitioning pending shipments to shipped status—has been met and manually accepted by the stakeholders.
