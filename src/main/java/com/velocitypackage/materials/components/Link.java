package com.velocitypackage.materials.components;

import java.util.Map;

public class Link implements Component
{
    private final String link;
    private final String name;
    
    public Link(String link, String name)
    {
        this.link = link;
        this.name = name;
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    @Override
    public void putStyle(HyperTextElement.STYLE option, String value)
    {
    }
    
    @Override
    public void onInteract(String id, Map<String, String> inputs)
    {
    }
    
    @Override
    public HyperTextElement getContent()
    {
        return new HyperTextElement(HyperTextElement.TAG.IFRAME)
        {
            @Override
            public String compile()
            {
                return "<a style=\"margin: 10px;\" href=\"" + link + "\">" + name + "</a>";
            }
        };
    }
}
