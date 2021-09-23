package net.cap5lut.growbox;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.rendering.vue.VueComponent;
import net.cap5lut.growbox.device.Device;
import net.cap5lut.growbox.device.DeviceManager;
import net.cap5lut.growbox.view.html.Node;
import net.cap5lut.growbox.view.html.elements.HTML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static net.cap5lut.growbox.view.html.DSL.*;

public class WebController {
    private static final Logger logger = LogManager.getLogger();

    private final DeviceManager deviceManager;

    public WebController(DeviceManager deviceManager, Javalin server) {
        this.deviceManager = deviceManager;
        server
                .before("/", this::prepare)
                .before("/device/*", this::prepare)
                .get("/", new VueComponent("growbox-overview"))
                .get("/device/{device_id}", this::getDevice);
    }

    private void prepare(Context context) {
        final var devices = deviceManager.list().join().toArray(Device[]::new);
        context.attribute("devices", devices);
    }

    private void getIndex(Context context) {

    }

    private void getDevice(Context context) {
        new VueComponent("growbox-device").handle(context);
    }
}
