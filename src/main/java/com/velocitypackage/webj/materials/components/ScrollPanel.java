package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;

public class ScrollPanel extends Panel
{
    
    public ScrollPanel(Component... children)
    {
        super(new Bootstrap[]{Bootstrap.OVERFLOW_SCROLL}, children);
    }
    
    public ScrollPanel(Align horizontal, Align vertical, Component... children)
    {
        super(new Bootstrap[]{Bootstrap.OVERFLOW_SCROLL}, horizontal, vertical, children);
    }
    
    public ScrollPanel(Bootstrap[] bootstraps, Component... children)
    {
        super(combine(new Bootstrap[]{Bootstrap.OVERFLOW_SCROLL}, bootstraps), children);
    }
    
    public ScrollPanel(Bootstrap[] bootstraps, Align horizontal, Align vertical, Component... children)
    {
        super(combine(new Bootstrap[]{Bootstrap.OVERFLOW_SCROLL}, bootstraps), horizontal, vertical, children);
    }
}
