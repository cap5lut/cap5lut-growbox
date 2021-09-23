package net.cap5lut.growbox.view.html.attributes;

import net.cap5lut.growbox.view.html.elements.Element;

import java.util.Optional;

public interface WithRel<E extends Element<E>> extends WithAttributes<E> {
    default Optional<String> rel() {
        return attr("rel");
    }

    default E rel(String value) {
        return attr("rel", value);
    }
}
