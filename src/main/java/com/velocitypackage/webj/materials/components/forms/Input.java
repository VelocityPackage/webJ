package com.velocitypackage.webj.materials.components.forms;

import com.velocitypackage.webj.materials.components.items.Item;
import com.velocitypackage.webj.materials.hypertext.*;

import java.util.Map;

/**
 * @author maxmielchen
 */
public class Input extends Item
{
    
    /**
     * Create a new input
     * @param inputType type of input as enum
     * @param value default value
     * @param placeholder placeholder as optional option
     */
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
    
    /**
     * Create a new input
     * @param inputType type of input as enum
     * @param value default value
     * @param placeholder placeholder as optional option
     * @param bootstraps bootstrap options
     */
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
        setHyperTextElement(new HyperTextElement(Tag.INPUT, combine(inputType.bootstraps, bootstraps), new Attribute[]{new Attribute(Attribute.AttributeIdentifier.VALUE, value), new Attribute(Attribute.AttributeIdentifier.PLACEHOLDER, placeholder), new Attribute(Attribute.AttributeIdentifier.TYPE, inputType.toString()), new Attribute(Attribute.AttributeIdentifier.REQUIRED, "")}, null));
    }
    
    /**
     * Create a new input
     * @param inputType type of input as enum
     * @param value default value
     * @param placeholder placeholder as optional option
     * @param styles style options
     */
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
