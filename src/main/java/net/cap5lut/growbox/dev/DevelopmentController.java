package net.cap5lut.growbox.dev;

import io.javalin.http.Context;
import io.javalin.plugin.rendering.vue.VueComponent;
import net.cap5lut.growbox.Application;
import net.cap5lut.growbox.Controller;

public class DevelopmentController extends Controller {

    public DevelopmentController(Application app) {
        super(app);
        app
                .webserver
                .get("/dev", this::dev);
    }

    private void dev(Context context) {
        new VueComponent("v-dev", state("greetings!")).handle(context);
    }
}
