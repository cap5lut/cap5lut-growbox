package net.cap5lut.growbox.view.html.attributes;

import net.cap5lut.growbox.view.html.elements.Element;

import java.util.Optional;

public interface WithAttributes<E extends Element<E>> {
    Optional<String> attr(String name);

    E attr(String name, String value);
}
