package com.velocitypackage.materials.components;

import java.util.Map;

public class Headline extends Component
{
    
    private final String text;
    private final int headline;
    
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
    }
    
    @Override
    public void add(Component component)
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
        headline.addStyle(HyperTextElement.STYLE.MARGIN, "10px");
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            headline.addStyle(style.getKey(), style.getValue());
        }
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            headline.addStyle(style.getKey(), style.getValue());
        }
        for (Bootstrap bootstrapClass : classes)
        {
            headline.addClass(bootstrap(bootstrapClass));
        }
        return headline;
    }
}
