package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;

/**
 * @author maxmielchen
 */
public class ScrollPanel extends Panel
{
    
    /**
     * Creates a panel with the option to scroll
     * @param children child components
     */
    public ScrollPanel(Component... children)
    {
        super(new Bootstrap[]{Bootstrap.OVERFLOW_SCROLL}, children);
    }
    
    /**
     * Creates a panel with the option to scroll
     * @param horizontal alignment
     * @param vertical alignment
     * @param children child components
     */
    public ScrollPanel(Align horizontal, Align vertical, Component... children)
    {
        super(new Bootstrap[]{Bootstrap.OVERFLOW_SCROLL}, horizontal, vertical, children);
    }
    
    /**
     * Creates a panel with the option to scroll
     * @param bootstraps style options
     * @param children child components
     */
    public ScrollPanel(Bootstrap[] bootstraps, Component... children)
    {
        super(combine(new Bootstrap[]{Bootstrap.OVERFLOW_SCROLL}, bootstraps), children);
    }
    
    /**
     * Creates a panel with the option to scroll
     * @param bootstraps style options
     * @param horizontal alignment
     * @param vertical alignment
     * @param children child components
     */
    public ScrollPanel(Bootstrap[] bootstraps, Align horizontal, Align vertical, Component... children)
    {
        super(combine(new Bootstrap[]{Bootstrap.OVERFLOW_SCROLL}, bootstraps), horizontal, vertical, children);
    }
}
