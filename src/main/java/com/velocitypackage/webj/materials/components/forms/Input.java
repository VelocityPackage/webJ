package com.velocitypackage.webj.materials.components.forms;

import com.velocitypackage.webj.materials.components.items.Item;
import com.velocitypackage.webj.materials.hypertext.*;

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
        setHyperTextElement(new HyperTextElement(Tag.INPUT, inputType.bootstraps, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.VALUE, value), new Attribute(Attribute.AttributeIdentifier.PLACEHOLDER, placeholder), new Attribute(Attribute.AttributeIdentifier.TYPE, inputType.toString()), new Attribute(Attribute.AttributeIdentifier.REQUIRED, "")}, null));
    }
    
    public Input(InputType inputType, String value, String placeholder, Bootstrap... bootstraps)
    {
        if (value == null)
        {
            value = "";
        }
        if (placeholder == null)
        {
            placeholder = "";
        }
        setHyperTextElement(new HyperTextElement(Tag.INPUT, super.combine(inputType.bootstraps, bootstraps), new Attribute[]{new Attribute(Attribute.AttributeIdentifier.VALUE, value), new Attribute(Attribute.AttributeIdentifier.PLACEHOLDER, placeholder), new Attribute(Attribute.AttributeIdentifier.TYPE, inputType.toString()), new Attribute(Attribute.AttributeIdentifier.REQUIRED, "")}, null));
    }
    
    public Input(InputType inputType, String value, String placeholder, Style... styles)
    {
        if (value == null)
        {
            value = "";
        }
        if (placeholder == null)
        {
            placeholder = "";
        }
        setHyperTextElement(new HyperTextElement(Tag.INPUT, inputType.bootstraps, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.VALUE, value), new Attribute(Attribute.AttributeIdentifier.PLACEHOLDER, placeholder), new Attribute(Attribute.AttributeIdentifier.TYPE, inputType.toString()), new Attribute(Attribute.AttributeIdentifier.REQUIRED, "")}, styles));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
