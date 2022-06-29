package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class Column extends Component
{
    public Column(Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.COL}, null, new Style[]{new Style(Style.StyleIdentifier.PADDING, "0px")}));
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
