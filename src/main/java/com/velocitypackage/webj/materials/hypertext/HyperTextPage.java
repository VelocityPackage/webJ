package com.velocitypackage.webj.materials.hypertext;

import java.util.Map;

/**
 * HyperTextPage is a class that represents html page.
 *
 * @author Max Mielchen
 */
public class HyperTextPage
{
    private final String path;
    private final HyperTextBehavior[] children;
    
    /**
     *
     * @param path path to the page
     * @param children children of the page
     */
    public HyperTextPage(String path, HyperTextBehavior... children)
    {
        this.path = path;
        this.children = children;
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
