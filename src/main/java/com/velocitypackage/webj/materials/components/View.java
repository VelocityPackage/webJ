package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class View extends Component
{
    public View(Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.CONTAINER}, null, null));
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
