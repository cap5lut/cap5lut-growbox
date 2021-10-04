package net.cap5lut.growbox.model;

import java.time.Instant;

public record DeviceData(Instant timestamp, float lightLevel, float temperature, float humidity,
                         float moisture) implements Model {
}
