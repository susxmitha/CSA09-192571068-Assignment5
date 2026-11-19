CREATE DATABASE IF NOT EXISTS transport_office;
USE transport_office;

CREATE TABLE IF NOT EXISTS bus_pass (
    pass_id VARCHAR(24) PRIMARY KEY,
    reg_no VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    route_no INT NOT NULL,
    amount DOUBLE NOT NULL,
    INDEX idx_route (route_no),
    INDEX idx_reg (reg_no)
);

CREATE TABLE IF NOT EXISTS wallet (
    wallet_id VARCHAR(30) PRIMARY KEY,
    balance DECIMAL(12,2) NOT NULL
);

INSERT IGNORE INTO wallet (wallet_id, balance)
VALUES ('HOSTEL', 10000.00), ('CANTEEN', 5000.00);
