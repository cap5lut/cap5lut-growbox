package net.cap5lut.growbox.controller;

import io.javalin.plugin.rendering.vue.VueComponent;
import net.cap5lut.growbox.Application;

public class DefaultController extends Controller {
    public DefaultController(Application application) {
        super(application);
        application
                .getWebserver()
                .get("/", new VueComponent("view-index"));
    }
}
