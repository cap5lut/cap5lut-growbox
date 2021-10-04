package net.cap5lut.growbox.model;

import java.time.Instant;

public record DeviceDataUpdate(String id, Instant timestamp, float lightLevel, float temperature, float humidity,
                               float moisture) implements Model {
}
