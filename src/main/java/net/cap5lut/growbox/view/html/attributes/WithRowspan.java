package net.cap5lut.growbox.view.html.attributes;

import net.cap5lut.growbox.view.html.elements.Element;

import java.util.OptionalInt;

public interface WithRowspan<E extends Element<E>> extends WithAttributes<E> {
    default OptionalInt rowspan() {
        return attr("rowspan").stream().mapToInt(Integer::parseInt).findAny();
    }

    default E rowspan(int value) {
        return attr("type", Integer.toString(value));
    }
}
