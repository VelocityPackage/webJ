package com.velocitypackage.materials.components;

import java.util.Map;

public class InlineFrame implements Component
{
    private final String link;
    
    public InlineFrame(String link)
    {
        this.link = link;
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
                return "<iframe src=\"" + link + "\">iFrame not supported</iframe>";
            }
        };
    }
}
