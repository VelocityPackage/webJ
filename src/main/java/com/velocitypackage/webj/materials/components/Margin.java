package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class Margin extends Component
{
    public Margin(Component... children)
    {
        setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, new Style[]{new Style(Style.StyleIdentifier.MARGIN, "10px")}));
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Margin(Integer margin, Component... children)
    {
        setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, new Style[]{new Style(Style.StyleIdentifier.MARGIN,  margin + "px")}));
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
