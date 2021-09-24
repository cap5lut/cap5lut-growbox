package net.cap5lut.growbox.device.data;

import net.cap5lut.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static net.cap5lut.growbox.Utils.sql;

public class DeviceDataManager {
    private final Database database;

    public DeviceDataManager(Database database) {
        this.database = database;
    }

    protected DeviceData read(ResultSet row) throws SQLException {
        return new DeviceData(
                row.getString("device_id"),
                row.getTimestamp("timestamp").getTime() / 1000,
                row.getFloat("light_level"),
                row.getFloat("temperature"),
                row.getFloat("humidity"),
                row.getFloat("moisture")
        );
    }

    public CompletableFuture<DeviceData> add(DeviceData data) {
        return database.transaction(ctx -> {
            ctx
                    .update(sql("/device/add"))
                    .addParameter(data.deviceId())
                    .addParameter((String) null)
                    .execute();
            ctx
                    .update(sql("/device/data/add"))
                    .addParameter(data.deviceId())
                    .addParameter(Instant.ofEpochSecond(data.timestamp()))
                    .addParameter(data.lightLevel())
                    .addParameter(data.temperature())
                    .addParameter(data.humidity())
                    .addParameter(data.moisture())
                    .execute();
            ctx.commit();
            return data;
        });
    }

    public CompletableFuture<Stream<DeviceData>> get(String deviceId, long since) {
        return database
                .query(sql("/device/data/get"))
                .addParameter(deviceId)
                .addParameter(Instant.ofEpochSecond(since))
                .execute(this::read);
    }

    public CompletableFuture<DeviceData> getLatest(String deviceId) {
        return database
                .query(sql("/device/data/latest"))
                .addParameter(deviceId)
                .execute(this::read)
                .thenApply(Stream::findFirst)
                .thenApply(Optional::orElseThrow);
    }
}
