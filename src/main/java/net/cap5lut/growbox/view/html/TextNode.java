package net.cap5lut.growbox.view.html;

import java.util.Locale;

public class TextNode extends Node {
    public String content;

    public String content() {
        return content;
    }

    public TextNode content(String text) {
        this.content = text;
        return this;
    }

    public TextNode content(String format, Object... arguments) {
        return content(String.format(format, arguments));
    }

    public TextNode content(Locale locale, String format, Object... arguments) {
        return content(String.format(locale, format, arguments));
    }

    @Override
    public void toString(StringBuilder content) {
        if (this.content != null) {
            content.append(this.content);
        }
    }

    @Override
    public String toString() {
        return content == null ? "" : content;
    }
}
