package net.cap5lut.growbox.manager;

import net.cap5lut.sql.Database;
import net.cap5lut.growbox.model.Device;
import net.cap5lut.growbox.model.DeviceData;
import net.cap5lut.growbox.model.DeviceDataUpdate;
import net.cap5lut.growbox.model.DeviceDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

public class DeviceManager {
    private static final Logger logger = LogManager.getLogger();

    private final Database database;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Map<String, Device> devicesById = new HashMap<>();

    public DeviceManager(Database database) {
        this.database = database;
        database
                .query(
                        """
                        SELECT device_id, name FROM growbox.devices;
                        """
                )
                .execute(row -> new Device(row.getString("device_id"), row.getString("name")))
                .join()
                .forEach(device -> devicesById.put(device.id(), device));
    }

    public CompletableFuture<DeviceDetails> getDeviceDetails(String id) {
        final Device device;
        lock.readLock().lock();
        try {
            device = devicesById.get(id);
        } finally {
            lock.readLock().unlock();
        }

        if (device == null) {
            return CompletableFuture.failedFuture(new NoSuchElementException());
        }

        return database
                .query(
                        """
                        SELECT
                            timestamp, light_level, temperature, humidity, moisture
                        FROM
                            growbox.device_data
                        WHERE
                            device_id = ?
                        ORDER BY
                            timestamp ASC
                        """
                )
                .addParameter(id)
                .execute(row -> new DeviceData(
                        row.getTimestamp("timestamp").toInstant(),
                        row.getFloat("light_level"),
                        row.getFloat("temperature"),
                        row.getFloat("humidity"),
                        row.getFloat("moisture")
                ))
                .thenApply(data -> new DeviceDetails(device, data.toList()));
    }

    public Optional<Device> getDevice(String id) {
        lock.readLock().lock();
        try {
            return Optional.ofNullable(devicesById.get(id));
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean hasDevice(String id) {
        lock.readLock().lock();
        try {
            return devicesById.containsKey(id);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Set<Device> getDevices() {
        lock.readLock().lock();
        try {
            return Set.copyOf(devicesById.values());
        } finally {
            lock.readLock().unlock();;
        }
    }

    public CompletableFuture<List<DeviceData>> getDeviceData(String deviceId) {
        return database
                .query(
                        """
                            SELECT
                                timestamp, temperature, light_level, temperature, humidity, moisture
                            FROM
                                growbox.device_data
                            WHERE
                                device_id = ?
                            ORDER BY
                                timestamp;
                            """
                )
                .addParameter(deviceId)
                .execute(row -> new DeviceData(
                        row.getTimestamp("timestamp").toInstant(),
                        row.getFloat("light_level"),
                        row.getFloat("temperature"),
                        row.getFloat("humidity"),
                        row.getFloat("moisture")
                ))
                .thenApply(Stream::toList);
    }

    public CompletableFuture<Device> store(Device device) {
        return database
                .update("""
                        INSERT INTO
                            growbox.devices (device_id, name)
                        VALUES (?, ?)
                        ON CONFLICT (device_id) DO UPDATE SET
                            name = EXCLUDED.name;
                        """)
                .addParameter(device.id())
                .addParameter(device.name())
                .execute()
                .thenApply(__ -> {
                    lock.writeLock().lock();
                    try {
                        devicesById.put(device.id(), device);
                        return device;
                    } finally {
                        lock.writeLock().unlock();
                    }
                });
    }

    public CompletableFuture<DeviceDataUpdate> store(DeviceDataUpdate deviceData) {
        return database
                .transaction(ctx -> {
                    ctx
                            .update(
                                    """
                                        INSERT INTO
                                            growbox.devices (device_id)
                                        VALUES (?)
                                        ON CONFLICT (device_id) DO NOTHING;
                                        """
                            )
                            .addParameter(deviceData.id())
                            .execute();

                    ctx
                            .update(
                                    """
                                        INSERT INTO
                                            growbox.device_data (device_id, timestamp, light_level, temperature, humidity, moisture)
                                        VALUES (?, ?, ?, ?, ?, ?)
                                        ON CONFLICT (device_id, timestamp)
                                        """
                            )
                            .addParameter(deviceData.id())
                            .addParameter(deviceData.timestamp())
                            .addParameter(deviceData.lightLevel())
                            .addParameter(deviceData.temperature())
                            .addParameter(deviceData.humidity())
                            .addParameter(deviceData.moisture())
                            .execute();

                    ctx.commit();
                    return deviceData;
                })
                .thenApply(data -> {
                    lock.writeLock().lock();
                    try {
                        devicesById.computeIfAbsent(data.id(), id -> new Device(id, null));
                    } finally {
                        lock.writeLock().unlock();
                    }
                    return data;
                });
    }
}
