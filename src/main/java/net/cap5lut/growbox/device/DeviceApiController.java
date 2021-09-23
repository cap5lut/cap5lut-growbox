package net.cap5lut.growbox.device;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import net.cap5lut.database.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.javalin.http.HttpCode.INTERNAL_SERVER_ERROR;
import static io.javalin.http.HttpCode.SERVICE_UNAVAILABLE;
import static java.util.stream.Collectors.*;

public class DeviceApiController {
    private static final Logger logger = LogManager.getLogger();

    private final DeviceManager deviceManager;

    public DeviceApiController(DeviceManager deviceManager, Javalin server) {
        this.deviceManager = deviceManager;
        server
                .get("/api/device", this::getApiDevice)
                .put("/api/device", this::putApiDevice)
                .get("/api/device/list", this::getDeviceList);
    }

    private void getApiDevice(Context context) {
        final var deviceId = context.queryParamAsClass("device_id", String.class).get();
        final var response = deviceManager.get(deviceId)
                .thenAccept(context::json)
                .exceptionally(ex -> {
                    logger.error("failed to fetch device {device_id={}}", deviceId, ex);
                    context.status(INTERNAL_SERVER_ERROR);
                    return null;
                });
        context.future(response);
    }

    private void putApiDevice(Context context) {
        context.status(SERVICE_UNAVAILABLE);
    }

    private void getDeviceList(Context context) {
        final var response = deviceManager.list()
                .thenApply(Stream::toArray)
                .thenAccept(context::json)
                .exceptionally(ex -> {
                    logger.error("failed to fetch device list", ex);
                    context.status(INTERNAL_SERVER_ERROR);
                    return null;
                });
        context.future(response);
    }
}
