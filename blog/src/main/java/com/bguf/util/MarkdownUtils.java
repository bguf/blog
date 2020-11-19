package com.bguf.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * @Author
 * @Description
 * @Date 11:13 上午 2020/10/9
 */
public class MarkdownUtils
{
    /***
     * markdown内容转html，方便界面浏览
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown)
    {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    /***
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown)
    {
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory()
                {
                    public AttributeProvider create(AttributeProviderContext context)
                {
                    return new CustomAttributeProvider();
                }
                }).build();
        return renderer.render(document);
    }

    static class CustomAttributeProvider implements AttributeProvider
    {
        public void setAttributes(Node node, String tagName, Map<String, String> attributes)
        {
            if (node instanceof Link)
            {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock)
            {
                attributes.put("class", "ui celled table");
            }
        }
    }
}
