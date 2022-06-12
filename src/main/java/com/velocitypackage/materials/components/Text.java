package com.velocitypackage.materials.components;

import java.util.HashMap;
import java.util.Map;

public class Text implements Component
{
    private final String text;
    
    private final Map<HyperTextElement.STYLE, String> styles;
    
    public Text(String text)
    {
        this.text = text;
        styles = new HashMap<>();
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    @Override
    public void putStyle(HyperTextElement.STYLE option, String value)
    {
        styles.put(option, value);
    }
    
    @Override
    public void onClick(String id)
    {
    }
    
    @Override
    public HyperTextElement getContent()
    {
        HyperTextElement text = new HyperTextElement(HyperTextElement.TAG.P, this.text);
        text.addStyle(HyperTextElement.STYLE.MARGIN_LEFT, "10px");
        text.addStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "10px");
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            text.addStyle(style.getKey(), style.getValue());
        }
        return text;
    }
}
