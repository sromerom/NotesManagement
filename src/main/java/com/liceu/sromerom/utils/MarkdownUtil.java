package com.liceu.sromerom.utils;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.renderer.text.TextContentRenderer;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class MarkdownUtil {
    public static String escapeText(String body) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(body);
        TextContentRenderer renderer2 = TextContentRenderer.builder().build();
        return renderer2.render(document);
    }

    public static String renderToHTML(String body) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(body);

        HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).sanitizeUrls(true).build();
        return renderer.render(document);
    }

    public static String cleanBody(String body) {
        PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
        System.out.println("Saneao: " + policy.sanitize(body));
        return policy.sanitize(body);
    }
}
