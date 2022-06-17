package com.velocitypackage.webj.materials.hypertext;

import java.util.HashMap;
import java.util.Map;

public final class HyperTextElement implements Element
{
    private Tag tag;
    private Map<Attribute, String> attributeMap;
    
    public HyperTextElement(Tag tag)
    {
        attributeMap = new HashMap<>();
        this.tag = tag;
    }
    
    /**
     * Write some attributes to the elements and when the attribute exist appends it to the existing.
     *
     * @param attribute keyword of the attribute (can't be null)
     * @param value     and the attribute option (can be null)
     */
    public void addAttribute(Attribute attribute, String value)
    {
        if (attribute == null)
        {
            return;
        }
        if (value == null)
        {
            value = "";
        }
        if (! attributeMap.containsKey(attribute))
        {
            attributeMap.put(attribute, value);
            return;
        }
        if (! (attributeMap.get(attribute).contains(value)))
        {
            attributeMap.put(attribute, attributeMap.get(attribute) + " " + value);
        }
    }
    
    /**
     * Adds some classes as Bootstrap enum to the element.
     *
     * @param classList (can't be null)
     */
    public void addBootstrap(Bootstrap... classList)
    {
        if (classList == null || classList.length == 0)
        {
            return;
        }
        for (Bootstrap c : classList)
        {
            addAttribute(Attribute.CLASS, c.name().toLowerCase().replaceAll("_", "-"));
        }
    }
    
    /**
     * Add a style option to the element or overwrite a style option.
     *
     * @param style the style option (can't be null)
     * @param value the value (can be null)
     */
    public void addStyle(Style style, String value)
    {
        if (style == null)
        {
            return;
        }
        addAttribute(Attribute.STYLE, style.name().toLowerCase().replaceAll("_", "-") + ": " + value + ";");
    }
    
}
