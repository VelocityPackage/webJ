package com.velocitypackage.materials.components;

import java.util.Map;

public class Text extends Component
{
    private final String text;
    
    public Text(String text)
    {
        this.text = text;
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    @Override
    public HyperTextElement getContent()
    {
        HyperTextElement text = new HyperTextElement(HyperTextElement.TAG.P, this.text);
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            text.addStyle(style.getKey(), style.getValue());
        }
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            text.addStyle(style.getKey(), style.getValue());
        }
        for (Bootstrap bootstrapClass : classes)
        {
            text.addClass(bootstrap(bootstrapClass));
        }
        return text;
    }
}
