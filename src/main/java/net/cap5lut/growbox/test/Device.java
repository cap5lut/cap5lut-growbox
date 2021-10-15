package net.cap5lut.growbox.test;

import java.time.Instant;

public interface Device {
    interface State {
        interface StateProperty {
            float current();
            float min();
            float max();
        }

        Instant timestamp();
        StateProperty lightLevel();
        StateProperty temperature();
        StateProperty humidity();
        StateProperty moisture();
    }
    String id();

    State current();
}
