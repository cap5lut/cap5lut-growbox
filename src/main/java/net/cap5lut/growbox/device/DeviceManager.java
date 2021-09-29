package net.cap5lut.growbox.device;

import net.cap5lut.database.Database;
import net.cap5lut.growbox.event.Event;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static net.cap5lut.growbox.Utils.sql;

public class DeviceManager {
    private final Database database;
    private final Map<String, Device> devices = new ConcurrentHashMap<>();

    public final Event<Device> onAdd = new Event<>();

    public DeviceManager(Database database) {
        this.database = database;
        database
                .query(sql("/device/list"))
                .execute(row -> new Device(row.getString("device_id"), row.getString("name")))
                .join()
                .forEach(device -> devices.put(device.id(), device));
    }

    public CompletableFuture<Device> add(Device device) {
        return database
                .update(sql("/device/add"))
                .addParameter(device.id())
                .addParameter(device.name())
                .execute()
                .thenApply(unused -> {
                    devices.put(device.id(), device);
                    onAdd.dispatch(device);
                    return device;
                });
    }

    public Optional<Device> get(String id) {
        return Optional.ofNullable(devices.get(id));
    }

    public List<Device> list() {
        return devices.values().stream().sorted(comparing(Device::id)).collect(Collectors.toUnmodifiableList());
    }
}
