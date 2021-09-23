package net.cap5lut.growbox;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Reference<T> {
    private T instance;

    public T get() {
        return requireNonNull(instance);
    }

    T set(T instance) {
        this.instance = instance;
        return instance;
    }
}
