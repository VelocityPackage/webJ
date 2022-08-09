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
    private final Style[] styles;
    private final Bootstrap[] bootstraps;
    private final HyperTextBehavior[] children;
    
    /**
     *
     * @param path path to the page
     * @param children children of the page
     */
    public HyperTextPage(String path, HyperTextBehavior... children)
    {
        this.path = path;
        this.styles = new Style[]{};
        this.bootstraps = new Bootstrap[]{};
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
        this.styles = styles;
        this.bootstraps = new Bootstrap[]{};
        this.children = children;
    }
    
    /**
     *
     * @param path path to the page
     * @param bootstraps bootstrap options for the page
     * @param children children of the page
     */
    public HyperTextPage(String path, Bootstrap[] bootstraps, HyperTextBehavior... children)
    {
        this.path = path;
        this.styles = new Style[]{};
        this.bootstraps = bootstraps;
        this.children = children;
    }
    
    /**
     *
     * @param path path to the page
     * @param styles style options for the page
     * @param bootstraps bootstrap options for the page
     * @param children children of the page
     */
    public HyperTextPage(String path, Style[] styles, Bootstrap[] bootstraps, HyperTextBehavior... children)
    {
        this.path = path;
        this.styles = styles;
        this.bootstraps = bootstraps;
        this.children = children;
    }
    
    /**
     * getter for the style options for the body
     * @return the style array
     */
    public Style[] getStyles()
    {
        return styles;
    }
    
    /**
     * getter for the bootstrap options for the body
     * @return the bootstraps array
     */
    public Bootstrap[] getBootstraps()
    {
        return bootstraps;
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
