package com.velocitypackage.materials.components;

public abstract class Button implements Component
{
    private final String name;
    public final String id;
    
    private final TYPE type;
    
    public Button(String name)
    {
        this.name = name;
        this.id = this.toString();
        this.type = TYPE.SECONDARY;
    }
    
    public Button(TYPE type, String name)
    {
        this.name = name;
        this.id = (String.valueOf(this.hashCode()).trim() + this.toString().trim());
        this.type = type;
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    public abstract void onClick();
    
    @Override
    public HyperTextElement getContent()
    {
        HyperTextElement button = new HyperTextElement(HyperTextElement.TAG.BUTTON, name);
        button.addId(id);
        button.addClass("btn");
        button.addClass("btn-" + type.name().toLowerCase().trim());
        button.addStyle(HyperTextElement.STYLE.MARGIN, "10px");
        return button;
    }
    
    public enum TYPE
    {
        PRIMARY, SECONDARY, SUCCESS, DANGER, WARNING, INFO, LIGHT, DARK
    }
}
