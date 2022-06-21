package com.velocitypackage.webj.materials.hypertext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public final class HyperTextElement
{
    private final Tag tag;
    private final List<Attribute> attributeList;
    private final String id;
    private final String text;
    
    /**
     *
     */
    public HyperTextElement(Tag tag, Bootstrap[] bootstraps, Attribute[] attributes)
    {
        this.text = "";
        this.tag = tag;
        this.attributeList = new ArrayList<>();
        this.id = String.valueOf(this.hashCode());
        if (bootstraps != null)
        {
            for (Bootstrap b : bootstraps)
            {
                this.addBootstrap(b);
            }
        }
        if (attributes != null)
        {
            for (Attribute a : attributes)
            {
                this.addAttribute(a);
            }
        }
        this.addAttribute(new Attribute(Attribute.AttributeIdentifier.ID, this.id));
    }
    
    public HyperTextElement(String text)
    {
        this.text = text;
        this.tag = null;
        this.attributeList = new ArrayList<>();
        this.id = String.valueOf(this.hashCode());
    }
    
    private void addAttribute(Attribute attribute)
    {
        if (attribute == null)
        {
            return;
        }
        if (! attributeList.contains(attribute))
        {
            attributeList.add(attribute);
        }
    }
    
    
    private void addBootstrap(Bootstrap... classList)
    {
        if (classList == null || classList.length == 0)
        {
            return;
        }
        for (Bootstrap c : classList)
        {
            addAttribute(new Attribute(Attribute.AttributeIdentifier.CLASS, c.name().replaceAll("_", "-").toLowerCase()));
        }
    }
    
    /**
     *
     */
    public String getTag()
    {
        if (tag == null)
        {
            return null;
        }
        return tag.name().replaceAll("_", "-").toLowerCase();
    }
    
    /**
     *
     */
    public String getAttributes()
    {
        Map<Attribute.AttributeIdentifier, String> map = new HashMap<>();
        for (Attribute a : attributeList)
        {
            if (! map.containsKey(a.getIdentifier()))
            {
                map.put(a.getIdentifier(), a.getValue());
            } else if (! map.containsValue(a.getValue()))
            {
                map.put(a.getIdentifier(), map.get(a.getIdentifier()) + " " + a.getValue());
            }
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        for (Attribute.AttributeIdentifier a : map.keySet())
        {
            stringBuilder.append(a.name().toLowerCase().replaceAll("_", "-"));
            stringBuilder.append("=\"");
            stringBuilder.append(map.get(a));
            stringBuilder.append("\" ");
        }
        return stringBuilder.toString();
    }
    
    public String getId()
    {
        return this.id;
    }
    
    public String getText()
    {
        return text;
    }
}
