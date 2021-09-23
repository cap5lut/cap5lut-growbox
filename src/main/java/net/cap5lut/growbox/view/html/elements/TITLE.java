package net.cap5lut.growbox.view.html.elements;

import java.util.Optional;

public class TITLE extends Element<TITLE> {
    private String content;

    public Optional<String> content() {
        return Optional.ofNullable(content);
    }

    public TITLE content(String value) {
        if (value == null || content.isBlank()) {
            content = null;
        } else {
            content = value;
        }
        return this;
    }

    public TITLE content(String format, Object... arguments) {
        return content(String.format(format, arguments));
    }

    @Override
    public TITLE append(net.cap5lut.growbox.view.html.Node child) {
        if (child instanceof net.cap5lut.growbox.view.html.TextNode) {
            return super.append(child);
        } else {
            throw new UnsupportedOperationException("title element must not contain any elements");
        }
    }
}
