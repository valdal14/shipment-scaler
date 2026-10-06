DROP TABLE IF EXISTS shipments;

CREATE TABLE shipments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tracking_reference VARCHAR(100) NOT NULL,
    net_weight DECIMAL(10, 2) NOT NULL NOT NULL CHECK (net_weight >= 0 AND net_weight <= 1000),
    tare_weight DECIMAL(10, 2) NOT NULL NOT NULL CHECK (tare_weight >= 0 AND tare_weight <= 1000),
    gross_weight DECIMAL(10, 2) NOT NULL NOT NULL CHECK (gross_weight >= 0 AND gross_weight <= 1000),
    status VARCHAR(10) NOT NULL
);

INSERT INTO shipments (tracking_reference, net_weight, tare_weight, gross_weight, status)
VALUES ('26e259e6-4dc1-4cb7-813e-d8ae407ac868', 50.0, 4.5, 54.5, 'PENDING')