DROP TABLE IF EXISTS shipments;

CREATE TABLE shipments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tracking_reference VARCHAR(6) NOT NULL,
    net_weight DECIMAL(10, 2) NOT NULL NOT NULL CHECK (net_weight >= 0 AND net_weight <= 1000),
    tare_weight DECIMAL(10, 2) NOT NULL NOT NULL CHECK (tare_weight >= 0 AND tare_weight <= 1000),
    gross_weight DECIMAL(10, 2) NOT NULL NOT NULL CHECK (gross_weight >= 0 AND gross_weight <= 1000)
);

INSERT INTO shipments (tracking_reference, net_weight, tare_weight, gross_weight)
VALUES ('TF0001', 50.0, 4.5, 54.5)