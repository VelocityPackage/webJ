package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.components.items.Item;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

/**
 * @author maxmielchen
 */
public class TextField extends Component
{
    
    /**
     * Creates a fixed field for Text items
     * @param children child items
     */
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
