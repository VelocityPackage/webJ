package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

/**
 * @author maxmielchen
 */
public class Margin extends Component
{
    /**
     * Make a margin fixed panel
     * @param children child components
     */
    public Margin(Component... children)
    {
        setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, new Style[]{new Style(Style.StyleIdentifier.MARGIN, "10px")}));
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    /**
     * Make a margin fixed panel
     * @param children child components
     * @param margin margin width / height
     */
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
