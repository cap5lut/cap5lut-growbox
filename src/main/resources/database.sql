CREATE SCHEMA IF NOT EXISTS growbox;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS growbox.devices (
    device_id VARCHAR(16) PRIMARY KEY,
    name VARCHAR(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS growbox.device_data (
    device_id VARCHAR(16) REFERENCES "growbox"."devices"(device_id) NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    light_level REAL NOT NULL,
    temperature REAL NOT NULL,
    humidity REAL NOT NULL,
    moisture REAL NOT NULL,

    PRIMARY KEY (device_id, timestamp)
);

CREATE TABLE IF NOT EXISTS growbox.plan (
    plan_id UUID PRIMARY KEY,
    name VARCHAR(64) UNIQUE NOT NULL
);