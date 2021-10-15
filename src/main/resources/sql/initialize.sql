CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE SCHEMA IF NOT EXISTS "cap5lut-growbox";
CREATE TABLE IF NOT EXISTS "cap5lut-growbox"."devices"
(
    deviceId VARCHAR(32) PRIMARY KEY NOT NULL
);
CREATE TABLE IF NOT EXISTS "cap5lut-growbox"."device_data"
(
    deviceId    VARCHAR(32)                 NOT NULL REFERENCES "cap5lut-growbox"."devices" (deviceId) ON UPDATE CASCADE ON DELETE CASCADE,
    timestamp   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    lightLevel  REAL                        NOT NULL,
    temperature REAL                        NOT NULL,
    humidity    REAL                        NOT NULL,
    moisture    REAL                        NOT NULL
);