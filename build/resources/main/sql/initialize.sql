CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS growbox;

CREATE TABLE IF NOT EXISTS growbox.devices (
    device_id VARCHAR(16) PRIMARY KEY,
    name VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS growbox.device_data_configuration (
    device_id VARCHAR(16) PRIMARY KEY REFERENCES growbox.devices(device_id),
    light_level REAL NOT NULL,
    temperature REAL NOT NULL,
    humidity REAL NOT NULL,
    moisture REAL NOT NULL
);

CREATE TABLE IF NOT EXISTS growbox.device_data (
    device_id VARCHAR(16) REFERENCES growbox.devices(device_id),
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    light_level REAL NOT NULL,
    temperature REAL NOT NULL,
    humidity REAL NOT NULL,
    moisture REAL NOT NULL
);