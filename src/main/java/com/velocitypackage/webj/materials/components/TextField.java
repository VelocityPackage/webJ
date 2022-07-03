package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.components.items.Item;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class TextField extends Component
{
    public TextField(Item... children)
    {
        setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, null));
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
