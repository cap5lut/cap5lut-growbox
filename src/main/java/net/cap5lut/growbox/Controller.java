package net.cap5lut.growbox;

import java.util.HashMap;
import java.util.HashSet;

public class Controller {
    protected final Application app;

    public Controller(Application app) {
        this.app = app;
    }

    protected State state() {
        return new StateImpl();
    }

    protected State state(Object model) {
        return state().set("model", model);
    }

    private static class StateImpl extends HashMap<String, Object> implements State {
        @Override
        public State set(String key, Object value) {
            put(key, value);
            return this;
        }
    }
}
