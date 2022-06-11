package com.velocitypackage.materials.components;

public class Text implements Component
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
    public void onClick(String id)
    {
    }
    
    @Override
    public HyperTextElement getHTML()
    {
        HyperTextElement text = new HyperTextElement(HyperTextElement.TAG.P, this.text);
        text.addStyle(HyperTextElement.STYLE.MARGIN_LEFT, "10px");
        text.addStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "10px");
        return text;
    }
}
