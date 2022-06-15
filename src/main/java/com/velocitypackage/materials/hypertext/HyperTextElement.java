package com.velocitypackage.materials.hypertext;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HyperTextElement
{
    private final Tag tag;
    private final List<HyperTextElement> content;
    private final Map<Attribute, String> attributeMap;
    
    public HyperTextElement(Tag tag)
    {
        this.tag = tag;
        this.content = new ArrayList<>();
        this.attributeMap = new HashMap<>();
    }
    
    public void addContent(HyperTextElement hyperTextElement)
    {
        content.add(hyperTextElement); // TODO: 15.06.22  
    }
    
    public void removeContent(HyperTextElement hyperTextElement)
    {
        content.remove(hyperTextElement); // TODO: 15.06.22
    }
    
    public void addAttribute(Attribute attribute, String value)
    {
        if (attributeMap.containsKey(attribute))
        {
            attributeMap.put(attribute, attributeMap.get(attribute) + " " + value);
        } else
        {
            attributeMap.put(attribute, value);
        }
    }
    
    public void removeAttribute(Attribute attribute, String value)
    {
        if (attributeMap.containsKey(attribute))
        {
            attributeMap.put(attribute, attributeMap.get(attribute).replaceAll(value, " "));
        }
    }
    
    public void addClass(String @NotNull ... classList)
    {
        for (String c : classList)
        {
            addAttribute(Attribute.CLASS, c);
        }
    }
    
    public void addClass(Bootstrap @NotNull ... classList)
    {
        for (Bootstrap c : classList)
        {
            addAttribute(Attribute.CLASS, c.name().toLowerCase().replaceAll("_", "-"));
        }
    }
    
    public void addId(String @NotNull ... idList)
    {
        for (String id : idList)
        {
            addAttribute(Attribute.ID, id);
        }
    }
    
    public void addStyle(@NotNull Style style, String value)
    {
        addAttribute(Attribute.STYLE, style.name().toLowerCase().replaceAll("_", "-") + ": " + value + ";");
    }
    
    public String generate()
    {
    
    }
    
    private String generateContent()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (HyperTextElement hyperTextElement : content)
        {
            stringBuilder.append(hyperTextElement.generate());
        }
        return new String(stringBuilder);
    }
    
    private String generateAttributes()
    
}
