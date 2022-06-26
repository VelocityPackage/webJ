package com.velocitypackage.webj.materials.components;

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
    
    public Panel(Color color, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, new Style[]{new Style(Style.StyleIdentifier.BACKGROUND, color.getHex())}));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Panel(Color color, Align contentAlign, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, new Style[]{new Style(Style.StyleIdentifier.BACKGROUND, color.getHex()), new Style(Style.StyleIdentifier.TEXT_ALIGN, contentAlign.name().toLowerCase())}));
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
