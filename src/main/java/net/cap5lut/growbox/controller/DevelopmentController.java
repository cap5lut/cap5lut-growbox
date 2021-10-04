package net.cap5lut.growbox.controller;

import io.javalin.plugin.rendering.vue.VueComponent;
import net.cap5lut.growbox.Application;

public class DevelopmentController extends Controller {
    public DevelopmentController(Application application) {
        super(application);
        application
                .getWebserver()
                .get("/dev", new VueComponent("view-dev"));
    }
}
