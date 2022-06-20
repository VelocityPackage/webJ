package com.velocitypackage.webj.materials.hypertext;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public final class HyperTextElement implements Element
{
    private final Tag tag;
    private final Map<Attribute, String> attributeMap;
    private final String id;
    
    /**
     *
     */
    public HyperTextElement(Tag tag, Bootstrap[] bootstraps, Tuple<Attribute, String>[] attributes, Tuple<Style, String>[] styles)
    {
        id = String.valueOf(this.hashCode());
        attributeMap = new HashMap<>();
        this.tag = tag;
        if (bootstraps != null)
        {
            for (Bootstrap b : bootstraps)
            {
                this.addBootstrap(b);
            }
        }
        if (attributes != null)
        {
            for (Tuple<Attribute, String> a : attributes)
            {
                this.addAttribute(a.key, a.value);
            }
        }
        if (styles != null)
        {
            for (Tuple<Style, String> s : styles)
            {
                this.addStyle(s.key, s.value);
            }
        }
        this.addAttribute(Attribute.ID, this.id);
    }
    
    /**
     * Write some attributes to the elements and when the attribute exist appends it to the existing.
     *
     * @param attribute keyword of the attribute (can't be null)
     * @param value     and the attribute option (can be null)
     */
    private void addAttribute(Attribute attribute, String value)
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
    private void addBootstrap(Bootstrap... classList)
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
    private void addStyle(Style style, String value)
    {
        if (style == null)
        {
            return;
        }
        addAttribute(Attribute.STYLE, style.name().toLowerCase().replaceAll("_", "-") + ": " + value + ";");
    }
    
    /**
     *
     */
    public String getTag()
    {
        return tag.name().toLowerCase();
    }
    
    /**
     *
     */
    public String getAttributes()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Attribute a : attributeMap.keySet())
        {
            stringBuilder.append(a.name().toLowerCase());
            stringBuilder.append("=\"");
            stringBuilder.append(attributeMap.get(a));
            stringBuilder.append("\" ");
        }
        return stringBuilder.toString();
    }
    
    public String getId()
    {
        return this.id;
    }
    
    public static class Tuple<K, V>
    {
        private final K key;
        private final V value;
        
        public Tuple(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
    }
    
}
