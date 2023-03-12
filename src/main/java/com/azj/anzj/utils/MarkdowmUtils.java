package com.azj.anzj.utils;


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

public class MarkdowmUtils {


    /**
    * markdown格式转换成Html
    * @Param markdown
    * @return
    * */
        public static String markdownToHtml(String markdown){
            Parser parser = Parser.builder().build();
            Node document = parser.parse(markdown);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            return renderer.render(document);
        }
        public static String markdownToHtmlExtensions(String markdown){
//h标题生成id
            Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
//            转换成table的Html
            List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
            Parser parser = Parser.builder()
                    .extensions(headingAnchorExtensions).build();
            Node document = parser.parse(markdown);
            HtmlRenderer renderer = HtmlRenderer.builder()
                    .extensions(tableExtension)
                    .attributeProviderFactory(new AttributeProviderFactory(){
                        public AttributeProvider create(AttributeProviderContext context){
                            return new CustomAttributeProvider();
                        }
                    }).build();
            return renderer.render(document);
        }
        static class CustomAttributeProvider implements AttributeProvider{
            @Override
            public void setAttributes(Node node, String tagName , Map<String,String> attributes){
                if(node instanceof Link){
                    attributes.put("target","_blank");
                }
                if(node instanceof TableBlock){
                    attributes.put("class","ui celled table");
                }
            }

        }


}
