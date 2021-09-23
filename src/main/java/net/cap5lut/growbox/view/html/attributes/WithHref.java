package net.cap5lut.growbox.view.html.attributes;

import net.cap5lut.growbox.view.html.elements.Element;

import java.util.Optional;

public interface WithHref<E extends Element<E>> extends WithAttributes<E> {
    default Optional<String> href() {
        return attr("href");
    }

    default E href(String value) {
        return attr("href", value);
    }
}
