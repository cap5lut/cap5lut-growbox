package net.cap5lut.growbox.model;

import java.util.List;

public record DeviceDetails(Device device, List<DeviceData> data) implements Model {
}
