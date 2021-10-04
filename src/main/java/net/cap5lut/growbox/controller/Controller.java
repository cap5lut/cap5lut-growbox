package net.cap5lut.growbox.controller;

import net.cap5lut.growbox.Application;
import net.cap5lut.growbox.State;

import java.util.HashMap;

public abstract class Controller {
    private static class StateImpl extends HashMap<String, Object> implements State {
        @Override
        public State add(String key, Object value) {
            put(key, value);
            return this;
        }
    }

    protected final Application application;

    protected Controller(Application application) {
        this.application = application;
    }

    protected State state(Object model) {
        return new StateImpl()
                .add("model", model)
                .add("devices", application.getDeviceManager().getDevices());
    }
}
