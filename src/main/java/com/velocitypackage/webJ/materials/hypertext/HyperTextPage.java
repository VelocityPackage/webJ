package com.velocitypackage.webJ.materials.hypertext;

import java.util.Map;

/**
 * HyperTextPage is a class that represents html page.
 *
 * @author Max Mielchen
 */
public class HyperTextPage
{
    private final String path;
    private final Style[] bodyStyles;
    private final HyperTextBehavior[] children;
    
    /**
     *
     * @param path path to the page
     * @param children children of the page
     */
    public HyperTextPage(String path, HyperTextBehavior... children)
    {
        this.path = path;
        this.bodyStyles = new Style[]{};
        this.children = children;
    }
    
    /**
     *
     * @param path path to the page
     * @param styles style options for the page
     * @param children children of the page
     */
    public HyperTextPage(String path, Style[] styles, HyperTextBehavior... children)
    {
        this.path = path;
        this.bodyStyles = styles;
        this.children = children;
    }
    
    /**
     * returns the style inert parameter
     * @return the style inert parameter
     */
    public String getStyleInert()
    {
        StringBuilder styleInterpretation = new StringBuilder();
        for (Style style : bodyStyles)
        {
            styleInterpretation.append(style.getIdentifier().name().toLowerCase()).append(":").append(style.getValue()).append(";");
        }
        return new String(styleInterpretation);
    }
    
    
    /**
     *
     * @return content presentation of the page as String
     */
    public String getTextRepresentation()
    {
        StringBuilder pageInterpretation = new StringBuilder();
        for (HyperTextBehavior child : children) {
            pageInterpretation.append(child.getTextRepresentation());
        }
        return new String(pageInterpretation);
    }
    
    /**
     *
     * @return path to the page
     */
    public String getPath()
    {
        return path;
    }
    
    /**
     * interaction with the page
     */
    public void onMessage(String id, Map<String, String> values)
    {
        for (HyperTextBehavior hyperTextBehavior : children)
        {
            hyperTextBehavior.onMessage(id, values);
        }
    }
}
