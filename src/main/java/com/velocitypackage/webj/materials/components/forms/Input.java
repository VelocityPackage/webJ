package com.velocitypackage.webj.materials.components.forms;

import com.velocitypackage.webj.materials.components.items.Item;
import com.velocitypackage.webj.materials.hypertext.Attribute;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class Input extends Item
{
    public Input(InputType inputType, String value, String placeholder)
    {
        if (value == null)
        {
            value = "";
        }
        if (placeholder == null)
        {
            placeholder = "";
        }
        super.setHyperTextElement(new HyperTextElement(Tag.INPUT, inputType.bootstraps, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.VALUE, value), new Attribute(Attribute.AttributeIdentifier.PLACEHOLDER, placeholder), new Attribute(Attribute.AttributeIdentifier.TYPE, inputType.toString())}, styles()));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
