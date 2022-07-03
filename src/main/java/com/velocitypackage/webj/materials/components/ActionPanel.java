package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class ActionPanel extends Component
{
    private final Bootstrap[] d_flex = new Bootstrap[]{Bootstrap.D_FLEX};
    private final Style[] styles = new Style[]{new Style(Style.StyleIdentifier.HEIGHT, "100%"), new Style(Style.StyleIdentifier.BACKGROUND, "none"), new Style(Style.StyleIdentifier.PADDING, "0px"), new Style(Style.StyleIdentifier.BORDER, "none")};
    
    private final Runnable runnable;
    
    public ActionPanel(Runnable r, Component... children)
    {
        this.runnable = r;
        super.setHyperTextElement(new HyperTextElement(Tag.BUTTON, null, null, this.styles));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public ActionPanel(Runnable r, Align horizontal, Align vertical, Component... children)
    {
        this.runnable = r;
        super.setHyperTextElement(new HyperTextElement(Tag.BUTTON, super.combine(d_flex, new Bootstrap[]{horizontal.horizontal, vertical.vertical}), null, this.styles));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public ActionPanel(Runnable r, Bootstrap[] bootstraps, Component... children)
    {
        this.runnable = r;
        super.setHyperTextElement(new HyperTextElement(Tag.BUTTON, bootstraps, null, this.styles));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public ActionPanel(Runnable r, Bootstrap[] bootstraps, Align horizontal, Align vertical, Component... children)
    {
        this.runnable = r;
        super.setHyperTextElement(new HyperTextElement(Tag.BUTTON, super.combine(super.combine(bootstraps, this.d_flex), new Bootstrap[]{horizontal.horizontal, vertical.vertical}), null, styles));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public ActionPanel(Runnable r, Style[] styles, Component... children)
    {
        this.runnable = r;
        super.setHyperTextElement(new HyperTextElement(Tag.BUTTON, null, null, super.combine(styles, this.styles)));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public ActionPanel(Runnable r, Style[] styles, Align horizontal, Align vertical, Component... children)
    {
        this.runnable = r;
        super.setHyperTextElement(new HyperTextElement(Tag.BUTTON, super.combine(this.d_flex, new Bootstrap[]{horizontal.horizontal, vertical.vertical}), null, combine(this.styles, styles)));
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
        runnable.run();
    }
}
