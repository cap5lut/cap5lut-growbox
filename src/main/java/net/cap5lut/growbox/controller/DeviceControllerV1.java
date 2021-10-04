package net.cap5lut.growbox.controller;

import io.javalin.http.Context;
import io.javalin.plugin.rendering.vue.VueComponent;
import net.cap5lut.growbox.Application;
import net.cap5lut.growbox.model.Device;
import net.cap5lut.growbox.model.DeviceDataUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;

import static java.util.concurrent.ForkJoinPool.commonPool;
import static net.cap5lut.growbox.ExceptionHandler.handle;

public class DeviceControllerV1 extends Controller {
    private static final Logger logger = LogManager.getLogger();

    public DeviceControllerV1(Application application) {
        super(application);
        application
                .getWebserver()
                .get("/api/v1/device", this::apiDeviceGet)
                .put("/api/v1/device", this::apiDevicePut)
                .get("/api/v1/device/data/{id}", this::apiDeviceDataGet)
                .put("/api/v1/device/data/{id}", this::apiDeviceDataPut)

                .get("/device/{id}", this::deviceGet);
    }

    private void apiDeviceGet(Context context) {
        final var id = context
                .queryParamAsClass("id", String.class)
                .check(deviceId -> deviceId != null && !deviceId.isBlank(), "invalid device id")
                .get();

        application
                .getDeviceManager()
                .getDevice(id)
                .ifPresentOrElse(
                        device -> context.status(200).json(device),
                        () -> context.status(404)
                );
    }

    private void apiDevicePut(Context context) {
        final var device = context
                .bodyValidator(Device.class)
                .check(d -> d.id() != null && !d.id().isBlank(), "invalid device id")
                .check(d -> d.name() == null || !d.name().isBlank(), "invalid device name")
                .get();

        final var response = application
                .getDeviceManager()
                .store(device)
                .thenRun(() -> {
                    context.status(204);
                    commonPool().execute(() -> application.getBroadcaster().send(device));
                })
                .exceptionally(ex -> {
                    logger.error("Failed to store device {device={})", device, ex);
                    context.status(500);
                    return null;
                });
        context.future(response);
    }

    private void apiDeviceDataGet(Context context) {
        final var deviceId = context
                .pathParamAsClass("id", String.class)
                .check(id -> id != null && !id.isBlank(), "invalid device id")
                .get();

        if (!application.getDeviceManager().hasDevice(deviceId)) {
            context.status(404);
            return;
        }

        final var response = application
                .getDeviceManager()
                .getDeviceData(deviceId)
                .thenAccept(data -> context.status(200).json(data))
                .exceptionally(ex -> {
                    logger.error("Failed to fetch device data {device id={}}", deviceId, ex);
                    return null;
                });
        context.future(response);
    }

    private void apiDeviceDataPut(Context context) {
        final var deviceData = context.bodyValidator(DeviceDataUpdate.class)
                .check(d -> d.id() != null && !d.id().isBlank(), "invalid device id")
                .get();

        final var response = application.getDeviceManager()
                .store(deviceData)
                .thenRun(() -> {
                    context.status(204);
                    commonPool().execute(() -> application.getBroadcaster().send(deviceData));
                })
                .exceptionally(ex -> {
                    logger.error("Failed to store device data {device data={}}", deviceData, ex);
                    return null;
                });
        context.future(response);
    }

    private void deviceGet(Context context) {
        final var deviceId = context.pathParam("id");
        application
                .getDeviceManager()
                .getDeviceDetails(deviceId)
                .thenAccept(details -> {

                })
                .exceptionally(handle(
                        NoSuchElementException.class, ex -> {
                            context.status(404);
                            return null;
                        },
                        ex -> {
                            logger.error("Failed to fetch device details {device id={}}", deviceId, ex);
                            return null;
                        }
                ));
        application
                .getDeviceManager()
                .getDevice(context.pathParam("id"))
                .ifPresentOrElse(
                        device -> new VueComponent(
                                "view-device-index",
                                state(null).add("device", device)
                        ).handle(context),
                        () -> new VueComponent("view-device-not-found").handle(context.status(404))
                );
    }
}
