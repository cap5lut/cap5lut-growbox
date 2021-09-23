package net.cap5lut.growbox.view.html.elements;

public class HTML extends Element<HTML> {
    private String doctype = "html";

    public String doctype() {
        return doctype;
    }

    public HTML doctype(String value) {
        doctype = value;
        return this;
    }

    @Override
    public void toString(StringBuilder content) {
        content.append("<!DOCTYPE ").append(doctype).append(">\n");
        super.toString(content);
    }
}
