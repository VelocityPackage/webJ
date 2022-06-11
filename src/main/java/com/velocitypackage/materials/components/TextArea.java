package com.velocitypackage.materials.components;

public class TextArea implements Component
{
    
    private final String text;
    
    public TextArea(String text)
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
        return new HyperTextElement(HyperTextElement.TAG.TEXTAREA, text);
    }
}
