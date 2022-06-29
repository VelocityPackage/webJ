package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class Panel extends Component
{
    
    public Panel(Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, null));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Panel(Align contentAlign, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, new Style[]{new Style(Style.StyleIdentifier.TEXT_ALIGN, contentAlign.name().toLowerCase())}));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Panel(Bootstrap[] bootstraps, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, bootstraps, null, null));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Panel(Bootstrap[] bootstraps, Align contentAlign, Component... children)
    {
        Style[] basic = new Style[]{new Style(Style.StyleIdentifier.TEXT_ALIGN, contentAlign.name().toLowerCase())};
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, bootstraps, null, basic));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Panel(Style[] styles, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, styles));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Panel(Style[] styles, Align contentAlign, Component... children)
    {
        Style[] basic = new Style[]{new Style(Style.StyleIdentifier.TEXT_ALIGN, contentAlign.name().toLowerCase())};
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, combine(basic, styles)));
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
