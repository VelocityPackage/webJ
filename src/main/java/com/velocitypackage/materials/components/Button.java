package com.velocitypackage.materials.components;

public abstract class Button implements Component
{
    private final String name;
    public final String id;
    
    private final TYPE type;
    
    public Button(String name, String id)
    {
        this.name = name;
        this.id = id;
        this.type = TYPE.SECONDARY;
    }
    
    public Button(TYPE type, String name, String id)
    {
        this.name = name;
        this.id = id;
        this.type = type;
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
        button.addClass("btn");
        button.addClass("btn-" + type.name().toLowerCase().trim());
        return button;
    }
    
    public enum TYPE
    {
        PRIMARY, SECONDARY, SUCCESS, DANGER, WARNING, INFO, LIGHT, DARK
    }
}
