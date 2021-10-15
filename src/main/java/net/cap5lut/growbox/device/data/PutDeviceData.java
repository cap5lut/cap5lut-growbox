package net.cap5lut.growbox.device.data;

public record PutDeviceData(String deviceId, long timestamp, float lightLevel, float temperature, float humidity,
                            float moisture) {
}
