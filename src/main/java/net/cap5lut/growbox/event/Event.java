package net.cap5lut.growbox.event;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Event<E> {
    @FunctionalInterface
    public interface Subscription {
        void cancel();
    }

    private final Map<BiConsumer<Subscription, E>, Subscription> listeners = new LinkedHashMap<>();

    public E dispatch(E event) {
        listeners.forEach((listener, subscription) -> listener.accept(subscription, event));
        return event;
    }

    public Subscription subscribe(BiConsumer<Subscription, E> listener) {
        return listeners.computeIfAbsent(listener, l -> () -> unsubscribe(l));
    }

    public Subscription subscribe(Consumer<E> listener) {
        return subscribe((subscription, event) -> listener.accept(event));
    }

    public void unsubscribe(BiConsumer<Subscription, E> listener) {
        listeners.remove(listener);
    }
}
