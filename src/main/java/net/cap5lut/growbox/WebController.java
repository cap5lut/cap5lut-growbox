package net.cap5lut.growbox;

import io.javalin.http.Context;
import io.javalin.plugin.rendering.vue.VueComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletionException;

public class WebController extends Controller {
    private static final Logger logger = LogManager.getLogger();

    public WebController(Application application) {
        super(application);
        app.webserver
                .get("/", new VueComponent("growbox-overview"))
                .get("/device/{device_id}", this::getDevice);
    }

    private void getDevice(Context context) {
        final var deviceId = context.pathParam("device_id");
        final var response = app.deviceDataManager
                .get(deviceId, 0)
                .thenApply(data -> {
                    final var device = app.deviceManager.get(deviceId).orElseThrow();
                    return new VueComponent(
                            "growbox-device",
                            model()
                                    .add("device", device)
                                    .add("device_data", data.toArray())
                    );
                })
                .exceptionally(ex -> {
                    if (ex instanceof CompletionException && ex.getCause() instanceof NoSuchElementException) {
                        return new VueComponent("growbox-device-not-found");
                    } else {
                        logger.error(ex);
                        return new VueComponent("error-500");
                    }
                })
                .thenAccept(handler -> handler.handle(context));
        context.future(response);
    }
}
