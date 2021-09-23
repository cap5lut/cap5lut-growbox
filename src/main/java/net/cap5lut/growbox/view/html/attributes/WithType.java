package net.cap5lut.growbox.view.html.attributes;

import net.cap5lut.growbox.view.html.elements.Element;

import java.util.Optional;

public interface WithType<E extends Element<E>> extends WithAttributes<E> {
    default Optional<String> type() {
        return attr("type");
    }

    default E type(String value) {
        return attr("type", value);
    }
}
