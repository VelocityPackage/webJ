package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

/**
 * @author maxmielchen
 */
public class Panel extends Component
{
    private final Bootstrap[] d_flex = new Bootstrap[]{Bootstrap.D_FLEX};
    private final Style[] styles = new Style[]{new Style(Style.StyleIdentifier.HEIGHT, "100%")};
    
    /**
     * Creates a panel´
     * @param children child components
     */
    public Panel(Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, this.styles));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    /**
     * Creates a panel
     * @param horizontal alignment
     * @param vertical alignment
     * @param children child components
     */
    public Panel(Align horizontal, Align vertical, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, combine(d_flex, new Bootstrap[]{horizontal.horizontal, vertical.vertical}), null, this.styles));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    /**
     * Creates a panel
     * @param bootstraps bootstrap options
     * @param children child components
     */
    public Panel(Bootstrap[] bootstraps, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, bootstraps, null, this.styles));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    /**
     * Creates a panel
     * @param bootstraps bootstrap options
     * @param horizontal alignment
     * @param vertical alignment
     * @param children child components
     */
    public Panel(Bootstrap[] bootstraps, Align horizontal, Align vertical, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, combine(combine(bootstraps, this.d_flex), new Bootstrap[]{horizontal.horizontal, vertical.vertical}), null, styles));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    /**
     * Creates a panel
     * @param styles style options
     * @param children child components
     */
    public Panel(Style[] styles, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, combine(styles, this.styles)));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    /**
     * Creates a panel
     * @param styles style options
     * @param horizontal alignment
     * @param vertical alignment
     * @param children child components
     */
    public Panel(Style[] styles, Align horizontal, Align vertical, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, combine(this.d_flex, new Bootstrap[]{horizontal.horizontal, vertical.vertical}), null, combine(this.styles, styles)));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
