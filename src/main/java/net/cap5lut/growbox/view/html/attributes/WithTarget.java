package net.cap5lut.growbox.view.html.attributes;

import net.cap5lut.growbox.view.html.elements.Element;

import java.util.Optional;

public interface WithTarget<E extends Element<E>> extends WithAttributes<E> {
    default Optional<String> target() {
        return attr("target");
    }

    default E target(String value) {
        return attr("target", value);
    }
}
