package net.cap5lut.growbox.device;

import net.cap5lut.database.Database;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static net.cap5lut.growbox.Utils.sql;

public class DeviceManager {
    private final Database database;

    public DeviceManager(Database database) {
        this.database = database;
    }

    public CompletableFuture<Device> add(Device data) {
        return database
                .update(sql("/device/add"))
                .addParameter(data.id())
                .addParameter(data.name())
                .execute()
                .thenCompose(unused -> completedFuture(data));
    }

    public CompletableFuture<Device> get(String id) {
        return database
                .query(sql("/device/get"))
                .addParameter(id)
                .execute(row -> new Device(row.getString("device_id"), row.getString("name")))
                .thenApply(Stream::findFirst)
                .thenApply(Optional::orElseThrow);
    }

    public CompletableFuture<Stream<Device>> list() {
        return database
                .query(sql("/device/list"))
                .execute(row -> new Device(row.getString("device_id"), row.getString("name")));
    }
}
