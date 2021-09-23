package net.cap5lut.growbox.device.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceData {
    @JsonProperty("device_id") private final String deviceId;
    @JsonProperty("timestamp") private final long timestamp;
    @JsonProperty("light_level") private final float lightLevel;
    @JsonProperty("temperature") private final float temperature;
    @JsonProperty("humidity") private final float humidity;
    @JsonProperty("moisture") private final float moisture;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DeviceData(
            @JsonProperty("device_id") String deviceId,
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("light_level") float lightLevel,
            @JsonProperty("temperature") float temperature,
            @JsonProperty("humidity") float humidity,
            @JsonProperty("moisture") float moisture) {
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.lightLevel = lightLevel;
        this.temperature = temperature;
        this.humidity = humidity;
        this.moisture = moisture;
    }

    public String deviceId() {
        return deviceId;
    }

    public long timestamp() {
        return timestamp;
    }

    public float lightLevel() {
        return lightLevel;
    }

    public float temperature() {
        return temperature;
    }

    public float humidity() {
        return humidity;
    }

    public float moisture() {
        return moisture;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "deviceId='" + deviceId + '\'' +
                ", timestamp=" + timestamp +
                ", lightLevel=" + lightLevel +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", moisture=" + moisture +
                '}';
    }
}
