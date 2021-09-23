package net.cap5lut.growbox.device;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Device {
    @JsonProperty("device_id") private final String id;
    @JsonProperty("name") private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Device(
            @JsonProperty("device_id") String id,
            @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
