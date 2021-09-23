package net.cap5lut.growbox.view.html;

import net.cap5lut.growbox.view.html.elements.A;
import net.cap5lut.growbox.view.html.elements.ARTICLE;
import net.cap5lut.growbox.view.html.elements.BODY;
import net.cap5lut.growbox.view.html.elements.DIV;
import net.cap5lut.growbox.view.html.elements.Element;
import net.cap5lut.growbox.view.html.elements.FOOTER;
import net.cap5lut.growbox.view.html.elements.H1;
import net.cap5lut.growbox.view.html.elements.H2;
import net.cap5lut.growbox.view.html.elements.H3;
import net.cap5lut.growbox.view.html.elements.H4;
import net.cap5lut.growbox.view.html.elements.H5;
import net.cap5lut.growbox.view.html.elements.H6;
import net.cap5lut.growbox.view.html.elements.HEAD;
import net.cap5lut.growbox.view.html.elements.HEADER;
import net.cap5lut.growbox.view.html.elements.HTML;
import net.cap5lut.growbox.view.html.elements.LI;
import net.cap5lut.growbox.view.html.elements.LINK;
import net.cap5lut.growbox.view.html.elements.MAIN;
import net.cap5lut.growbox.view.html.elements.NAV;
import net.cap5lut.growbox.view.html.elements.OL;
import net.cap5lut.growbox.view.html.elements.SCRIPT;
import net.cap5lut.growbox.view.html.elements.SPAN;
import net.cap5lut.growbox.view.html.elements.STYLE;
import net.cap5lut.growbox.view.html.elements.TABLE;
import net.cap5lut.growbox.view.html.elements.TBODY;
import net.cap5lut.growbox.view.html.elements.TD;
import net.cap5lut.growbox.view.html.elements.TH;
import net.cap5lut.growbox.view.html.elements.THEAD;
import net.cap5lut.growbox.view.html.elements.TITLE;
import net.cap5lut.growbox.view.html.elements.TR;
import net.cap5lut.growbox.view.html.elements.UL;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DSL {
    private static <E extends Element<E>> E create(
            Supplier<E> factory, Consumer<E> preModifier, Consumer<E> postModifier, Node... children
    ) {
        final var element = factory.get();
        if (preModifier != null) {
            preModifier.accept(element);
        }
        element.append(children);
        if (postModifier != null) {
            postModifier.accept(element);
        }
        return element;
    }

    public static TextNode $(String text) {
        return new TextNode().content(text);
    }

    public static TextNode $(String format, Object... arguments) {
        return new TextNode().content(format, arguments);
    }

    public static TextNode $(Locale locale, String format, Object... arguments) {
        return new TextNode().content(locale, format, arguments);
    }

    public static A $a(String href, Node... children) {
        return create(A::new, e -> e.href(href), null, children).href(href);
    }

    public static A $a(String href, Consumer<A> modifier, Node... children) {
        return create(A::new, e -> e.href(href), modifier, children).href(href);
    }

    public static ARTICLE $article(Node... children) {
        return create(ARTICLE::new, null, null, children);
    }

    public static ARTICLE $article(Consumer<ARTICLE> modifier, Node... children) {
        return create(ARTICLE::new, null, modifier, children);
    }

    public static BODY $body(Node... children) {
        return create(BODY::new, null, null, children);
    }

    public static BODY $body(Consumer<BODY> modifier, Node... children) {
        return create(BODY::new, null, modifier, children);
    }

    public static DIV $div(Node... children) {
        return create(DIV::new, null, null, children);
    }

    public static DIV $div(Consumer<DIV> modifier, Node... children) {
        return create(DIV::new, null, modifier, children);
    }

    public static FOOTER $footer(Node... children) {
        return create(FOOTER::new, null, null, children);
    }

    public static FOOTER $footer(Consumer<FOOTER> modifier, Node... children) {
        return create(FOOTER::new, null, modifier, children);
    }

    public static H1 $h1(Node... children) {
        return create(H1::new, null, null, children);
    }

    public static H1 $h1(Consumer<H1> modifier, Node... children) {
        return create(H1::new, null, modifier, children);
    }

    public static H2 $h2(Node... children) {
        return create(H2::new, null, null, children);
    }

    public static H2 $h2(Consumer<H2> modifier, Node... children) {
        return create(H2::new, null, modifier, children);
    }

    public static H3 $h3(Node... children) {
        return create(H3::new, null, null, children);
    }

    public static H3 $h3(Consumer<H3> modifier, Node... children) {
        return create(H3::new, null, modifier, children);
    }

    public static H4 $h4(Node... children) {
        return create(H4::new, null, null, children);
    }

    public static H4 $h4(Consumer<H4> modifier, Node... children) {
        return create(H4::new, null, modifier, children);
    }

    public static H5 $h5(Node... children) {
        return create(H5::new, null, null, children);
    }

    public static H5 $h5(Consumer<H5> modifier, Node... children) {
        return create(H5::new, null, modifier, children);
    }

    public static H6 $h6(Node... children) {
        return create(H6::new, null, null, children);
    }

    public static H6 $h6(Consumer<H6> modifier, Node... children) {
        return create(H6::new, null, modifier, children);
    }

    public static HEAD $head(Node... children) {
        return create(HEAD::new, null, null, children);
    }

    public static HEAD $head(Consumer<HEAD> modifier, Node... children) {
        return create(HEAD::new, null, modifier, children);
    }

    public static HEADER $header(Node... children) {
        return create(HEADER::new, null, null, children);
    }

    public static HEADER $header(Consumer<HEADER> modifier, Node... children) {
        return create(HEADER::new, null, modifier, children);
    }

    public static HTML $html(Node... children) {
        return create(HTML::new, null, null, children);
    }

    public static HTML $html(Consumer<HTML> modifier, Node... children) {
        return create(HTML::new, null, modifier, children);
    }

    public static LI $li(Node... children) {
        return create(LI::new, null, null, children);
    }

    public static LI $li(Consumer<LI> modifier, Node... children) {
        return create(LI::new, null, modifier, children);
    }

    public static LINK $link(String href) {
        return create(LINK::new, e -> e.rel("stylesheet").href(href), null);
    }

    public static LINK $link(String rel, String href) {
        return create(LINK::new, e -> e.rel(rel).href(href), null);
    }

    public static MAIN $main(Node... children) {
        return create(MAIN::new, null, null, children);
    }

    public static MAIN $main(Consumer<MAIN> modifier, Node... children) {
        return create(MAIN::new, null, modifier, children);
    }

    public static NAV $nav(Node... children) {
        return create(NAV::new, null, null, children);
    }

    public static NAV $nav(Consumer<NAV> modifier, Node... children) {
        return create(NAV::new, null, modifier, children);
    }

    public static OL $ol(Node... children) {
        return create(OL::new, null, null, children);
    }

    public static OL $ol(Consumer<OL> modifier, Node... children) {
        return create(OL::new, null, modifier, children);
    }

    public static SCRIPT $script(String href) {
        return create(SCRIPT::new, e -> e.href(href), null);
    }

    public static SCRIPT $script(String type, String href) {
        return create(SCRIPT::new, e -> e.type(type).href(href), null);
    }

    public static SCRIPT $script(Node... children) {
        return create(SCRIPT::new, null, null, children);
    }

    public static SCRIPT $script(String type, Node... children) {
        return create(SCRIPT::new, e -> e.type(type), null, children);
    }

    public static SPAN $span(Node... children) {
        return create(SPAN::new, null, null, children);
    }

    public static SPAN $span(Consumer<SPAN> modifier, Node... children) {
        return create(SPAN::new, null, modifier, children);
    }

    public static STYLE $style(Node... children) {
        return create(STYLE::new, null, null, children);
    }

    public static STYLE $style(String type, Node... children) {
        return create(STYLE::new, e -> e.type(type), null, children);
    }

    public static TABLE $table(Node... children) {
        return create(TABLE::new, null, null, children);
    }

    public static TABLE $table(Consumer<TABLE> modifier, Node... children) {
        return create(TABLE::new, null, modifier, children);
    }

    public static TBODY $tbody(Node... children) {
        return create(TBODY::new, null, null, children);
    }

    public static TBODY $tbody(Consumer<TBODY> modifier, Node... children) {
        return create(TBODY::new, null, modifier, children);
    }

    public static TD $td(Node... children) {
        return create(TD::new, null, null, children);
    }

    public static TD $td(Consumer<TD> modifier, Node... children) {
        return create(TD::new, null, modifier, children);
    }

    public static TH $th(Node... children) {
        return create(TH::new, null, null, children);
    }

    public static TH $th(Consumer<TH> modifier, Node... children) {
        return create(TH::new, null, modifier, children);
    }

    public static THEAD $thead(Node... children) {
        return create(THEAD::new, null, null, children);
    }

    public static THEAD $thead(Consumer<THEAD> modifier, Node... children) {
        return create(THEAD::new, null, modifier, children);
    }

    public static TR $tr(Node... children) {
        return create(TR::new, null, null, children);
    }

    public static TR $tr(Consumer<TR> modifier, Node... children) {
        return create(TR::new, null, modifier, children);
    }

    public static TITLE $title(String title) {
        return create(TITLE::new, null, null, $(title));
    }

    public static TITLE $title(String format, Object... arguments) {
        return create(TITLE::new, null, null, $(format, arguments));
    }

    public static TITLE $title(Consumer<TITLE> modifier, String title) {
        return create(TITLE::new, null, modifier, $(title));
    }

    public static TITLE $title(Consumer<TITLE> modifier, String format, Object arguments) {
        return create(TITLE::new, null, modifier, $(format, arguments));
    }

    public static UL $ul(Node... children) {
        return create(UL::new, null, null, children);
    }

    public static UL $ul(Consumer<UL> modifier, Node... children) {
        return create(UL::new, null, modifier, children);
    }
}
