package net.cap5lut.growbox.view.html.attributes;

import net.cap5lut.growbox.view.html.elements.Element;

import java.util.OptionalInt;

public interface WithColspan<E extends Element<E>> extends WithAttributes<E> {
    default OptionalInt colspan() {
        return attr("rowspan").stream().mapToInt(Integer::parseInt).findAny();
    }

    default E colspan(int value) {
        return attr("type", Integer.toString(value));
    }
}
