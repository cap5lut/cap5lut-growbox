package net.cap5lut.growbox;

import java.util.HashMap;

public abstract class Controller {
    protected final Application app;

    private class DefaultViewModel extends HashMap<String, Object> implements ViewModel {
        public DefaultViewModel() {
            put("devices", app.deviceManager.list());
        }

        @Override
        public ViewModel add(String key, Object data) {
            put(key, data);
            return this;
        }
    }

    protected Controller(Application app) {
        this.app = app;
    }

    protected Application app() {
        return app;
    }

    public ViewModel model() {
        return new DefaultViewModel();
    }
}
