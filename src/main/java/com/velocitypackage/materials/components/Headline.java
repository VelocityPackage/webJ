package com.velocitypackage.materials.components;

import java.util.HashMap;
import java.util.Map;

public class Headline implements Component
{
    
    private final String text;
    private final int headline;
    
    private final Map<HyperTextElement.STYLE, String> styles;
    
    /**
     * @param text     Value
     * @param headline only between 1 - 6
     */
    public Headline(String text, int headline)
    {
        this.text = text;
        if (headline > 6 || headline < 1)
        {
            this.headline = 1;
        } else
        {
            this.headline = headline;
        }
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
        HyperTextElement headline = new HyperTextElement(HyperTextElement.TAG.H1, text);
        switch (this.headline)
        {
            case 1:
                headline = new HyperTextElement(HyperTextElement.TAG.H1, text);
                break;
            case 2:
                headline = new HyperTextElement(HyperTextElement.TAG.H2, text);
                break;
            case 3:
                headline = new HyperTextElement(HyperTextElement.TAG.H3, text);
                break;
            case 4:
                headline = new HyperTextElement(HyperTextElement.TAG.H4, text);
                break;
            case 5:
                headline = new HyperTextElement(HyperTextElement.TAG.H5, text);
                break;
            case 6:
                headline = new HyperTextElement(HyperTextElement.TAG.H6, text);
                break;
        }
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            headline.addStyle(style.getKey(), style.getValue());
        }
        return headline;
    }
}
