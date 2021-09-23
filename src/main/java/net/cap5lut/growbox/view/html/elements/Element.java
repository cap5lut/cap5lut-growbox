package net.cap5lut.growbox.view.html.elements;

import net.cap5lut.growbox.view.html.Node;
import net.cap5lut.growbox.view.html.attributes.WithAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class Element<E extends Element<E>> extends Node implements WithAttributes<E> {
    private final Map<String, String> attributes = new HashMap<>();
    private final List<Node> children = new ArrayList<>();

    @Override
    public Optional<String> attr(String name) {
        return Optional.ofNullable(attributes.get(name));
    }

    @SuppressWarnings("unchecked")
    @Override
    public E attr(String name, String value) {
        if (value == null || value.isBlank()) {
            attributes.remove(name);
        } else {
            attributes.put(name, value);
        }
        return (E) this;
    }

    public String tag() {
        return getClass().getSimpleName().toLowerCase(Locale.ROOT);
    }

    public Optional<Set<String>> classes() {
        return attr("class").map(classes -> classes.split(" ")).map(Set::of);
    }

    public E classes(String value) {
        return attr("class", value);
    }

    public E classes(String ... values) {
        return attr("class", String.join(" ", Set.of(values)));
    }

    public Optional<String> data(String name) {
        return attr("data-" + name);
    }

    public E data(String name, String value) {
        return attr("data-" + name, value);
    }

    public Optional<String> id() {
        return attr("id");
    }

    public E id(String value) {
        return attr("id", value);
    }

    public Optional<String> name() {
        return attr("name");
    }

    public E name(String value) {
        return attr("name", value);
    }

    @SuppressWarnings("unchecked")
    public E append(Node child) {
        this.children.add(child);
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E append(Stream<? extends Node> children) {
        children.forEach(this::append);
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public <N extends Node> E append(N... children) {
        return append(Arrays.stream(children));
    }

    public E append(Iterable<? extends Node> children) {
        return append(StreamSupport.stream(children.spliterator(), false));
    }

    @Override
    public void toString(StringBuilder content) {
        content.append('<').append(tag());
        for (final var attribute: attributes.entrySet()) {
            content.append(' ').append(attribute.getKey()).append("=\"").append(attribute.getValue()).append('"');
        }
        content.append('>');
        for (final var child: children) {
            child.toString(content);
        }
        content.append("</").append(tag()).append(">");
    }

    @Override
    public String toString() {
        final var content = new StringBuilder();
        toString(content);
        return content.toString();
    }
}
