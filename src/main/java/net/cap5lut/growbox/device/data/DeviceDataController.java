package net.cap5lut.growbox.device.data;

import io.javalin.http.Context;
import net.cap5lut.growbox.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.CompletionException;

import static java.util.concurrent.CompletableFuture.runAsync;
import static net.cap5lut.growbox.Application.sql;

public class DeviceDataController {
    private final Logger logger = LogManager.getLogger();
    private final Application application;

    public DeviceDataController(Application application) {
        this.application = application;
        application
                .webserver
                .put("/api/v1/device/data", this::putDeviceData);
    }


    private void putDeviceData(Context context) {
        final var deviceData = context
                .bodyValidator(PutDeviceData.class)
                .check(data -> data.deviceId() != null && data.deviceId().isBlank(), "invalid device id")
                .get();

        context.future(runAsync(() -> {
            try (final var connection = application.dataSource.getConnection()) {
                connection.setAutoCommit(false);
                try {
                    try (final var statement = connection.prepareStatement(sql("device/insert"))) {
                        statement.setString(1, deviceData.deviceId());
                    }
                    try (final var statement = connection.prepareStatement(sql("device/data/insert"))) {
                        statement.setString(1, deviceData.deviceId());
                        statement.setTimestamp(2, Timestamp.from(Instant.ofEpochSecond(deviceData.timestamp())));
                        statement.setFloat(3, deviceData.lightLevel());
                        statement.setFloat(4, deviceData.temperature());
                        statement.setFloat(5, deviceData.humidity());
                        statement.setFloat(6, deviceData.moisture());
                    }
                    connection.commit();
                } catch (SQLException error) {
                    connection.rollback();
                    throw error;
                }
            } catch (SQLException error) {
                throw new CompletionException(error);
            }
            context.status(204);
        }).exceptionally(error -> {
            logger.atError().log("Failed to store device data ({})", deviceData, error);
            context.status(500);
            return null;
        }));
    }
}
