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
        Style[] basic = new Style[]{new Style(Style.StyleIdentifier.TEXT_ALIGN, contentAlign.name().toLowerCase())
        };
        Style[] combine = new Style[basic.length + styles.length];
        System.arraycopy(basic, 0, combine, 0, basic.length);
        System.arraycopy(styles, 0, combine, basic.length, styles.length);
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, combine));
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
