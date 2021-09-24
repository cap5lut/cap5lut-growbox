package net.cap5lut.growbox;

import io.javalin.http.Context;
import io.javalin.plugin.rendering.vue.VueComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebController extends Controller {
    private static final Logger logger = LogManager.getLogger();

    public WebController(Application application) {
        super(application);
        app.webserver
                .get("/", new VueComponent("growbox-overview"))
                .get("/device/{device_id}", this::getDevice);
    }

    private void getDevice(Context context) {
        final var response = app.deviceDataManager
                .getLatest(context.pathParam("device_id"))
                .thenApply(data -> {
                    final var device = app.deviceManager.get(data.deviceId()).orElseThrow();
                    return new VueComponent(
                            "growbox-device",
                            model()
                                    .add("device", device)
                                    .add("device_data", data)
                    );
                })
                .exceptionally(ex -> {
                    logger.error("failed", ex);
                    return new VueComponent("growbox-device-not-found");
                })
                .thenAccept(handler -> handler.handle(context));
        context.future(response);

        /*app.deviceManager
                .get(context.pathParam("device_id"))
                .map(device -> new VueComponent("growbox-device", model().add("device", device)))
                .orElseGet(() -> new VueComponent("growbox-device-not-found"))
                .handle(context);
        */
    }
}
