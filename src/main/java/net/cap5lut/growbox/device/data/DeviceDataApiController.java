package net.cap5lut.growbox.device.data;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Stream;

import static io.javalin.http.HttpCode.INTERNAL_SERVER_ERROR;
import static io.javalin.http.HttpCode.NO_CONTENT;
import static java.util.stream.Collectors.toList;

public class DeviceDataApiController {
    private static final Logger logger = LogManager.getLogger();

    private final DeviceDataManager deviceDataManager;

    public DeviceDataApiController(DeviceDataManager deviceDataManager, Javalin server) {
        this.deviceDataManager = deviceDataManager;
        server
                .get("/api/device/data", this::getApiDeviceData)
                .put("/api/device/data", this::putApiDeviceData);
    }

    private void getApiDeviceData(Context context) {
        final var deviceId = context.queryParamAsClass("device_id", String.class).get();
        final var since = context.queryParamAsClass("since", Long.class).get();
        final var response= deviceDataManager.get(deviceId, since)
                .thenApply(Stream::toArray)
                .thenAccept(context::json)
                .exceptionally(ex -> {
                    logger.error("Failed to fetch data {deviceId={}, since={}}", deviceId, since, ex);
                    context.status(INTERNAL_SERVER_ERROR);
                    return null;
                });
        context.future(response);
    }

    private void putApiDeviceData(Context context) {
        final var data = context.bodyAsClass(DeviceData.class);
        final var response = deviceDataManager.add(data)
                .thenRun(() -> context.status(NO_CONTENT))
                .exceptionally(ex -> {
                    logger.error("Failed to store data {data={}}", data, ex);
                    context.status(INTERNAL_SERVER_ERROR);
                    return null;
                });
        context.future(response);
    }
}
