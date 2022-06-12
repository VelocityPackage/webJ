package com.velocitypackage.materials.components;

import java.util.HashMap;
import java.util.Map;

public abstract class Button implements Component
{
    private final String name;
    public final String id;
    
    private final TYPE type;
    
    private final Map<HyperTextElement.STYLE, String> styles;
    
    public Button(String name)
    {
        this.name = name;
        this.id = this.toString();
        this.type = TYPE.SECONDARY;
        styles = new HashMap<>();
    }
    
    public Button(TYPE type, String name)
    {
        this.name = name;
        this.id = (String.valueOf(this.hashCode()).trim() + this.toString().trim());
        this.type = type;
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
    
    public abstract void onClick();
    
    @Override
    public HyperTextElement getContent()
    {
        HyperTextElement button = new HyperTextElement(HyperTextElement.TAG.BUTTON, name);
        button.addId(id);
        button.addClass("btn");
        button.addClass("btn-" + type.name().toLowerCase().trim());
        button.addStyle(HyperTextElement.STYLE.MARGIN, "10px");
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            button.addStyle(style.getKey(), style.getValue());
        }
        return button;
    }
    
    public enum TYPE
    {
        PRIMARY, SECONDARY, SUCCESS, DANGER, WARNING, INFO, LIGHT, DARK
    }
}
