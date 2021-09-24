package net.cap5lut.growbox.device;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import net.cap5lut.database.Database;
import net.cap5lut.growbox.Application;
import net.cap5lut.growbox.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.javalin.http.HttpCode.SERVICE_UNAVAILABLE;

public class DeviceApiController extends Controller {
    private static final Logger logger = LogManager.getLogger();

    public DeviceApiController(Application application) {
        super(application);
        app.webserver
                .get("/api/device", this::getApiDevice)
                .put("/api/device", this::putApiDevice)
                .get("/api/device/list", this::getDeviceList);
    }

    private void getApiDevice(Context context) {
        app.deviceManager
                .get(context.queryParam("device_id"))
                .ifPresentOrElse(context::json, () -> context.status(404));

    }

    private void putApiDevice(Context context) {
        context.status(SERVICE_UNAVAILABLE);
    }

    private void getDeviceList(Context context) {
        context.json(app.deviceManager.list());
    }
}
