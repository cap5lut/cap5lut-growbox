package net.cap5lut.growbox.view.html.attributes;

import net.cap5lut.growbox.view.html.elements.Element;

import java.util.Optional;

public interface WithSrc<E extends Element<E>> extends WithAttributes<E> {
    default Optional<String> src() {
        return attr("src");
    }

    default E href(String value) {
        return attr("src", value);
    }
}
