package net.cap5lut.growbox.dev;

import io.javalin.http.Context;
import io.javalin.plugin.rendering.vue.VueComponent;
import net.cap5lut.growbox.Application;

public class DevelopmentController {
    private final Application application;

    public DevelopmentController(Application application) {
        this.application = application;
        application
                .webserver
                .get("/dev", this::dev);
    }

    private void dev(Context context) {
        new VueComponent("")
    }
}
