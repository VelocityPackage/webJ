package com.velocitypackage.materials.components;

public abstract class Button implements Component
{
    private final String name;
    public final String id;
    
    public Button(String name, String id)
    {
        this.name = name;
        this.id = id;
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    public abstract void onClick();
    
    @Override
    public HyperTextElement getHTML()
    {
        HyperTextElement button = new HyperTextElement(HyperTextElement.TAG.BUTTON, name);
        button.addId(id);
        return button;
    }
}
