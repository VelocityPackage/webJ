package com.velocitypackage.materials.components;

import java.util.Map;
import java.util.Objects;

public abstract class Button extends Component
{
    private final String name;
    private final String id;
    private final TYPE type;
    
    public Button(String name)
    {
        this.name = name;
        this.id = String.valueOf(this.hashCode());
        this.type = TYPE.SECONDARY;
    }
    
    public Button(String name, TYPE type)
    {
        this.name = name;
        this.id = String.valueOf(this.hashCode());
        this.type = type;
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    @Override
    public void onInteract(String id, Map<String, String> inputs)
    {
        if (Objects.equals(this.id.trim(), id.trim()))
        {
            onClick();
        }
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
        for (Bootstrap bootstrapClass : classes)
        {
            button.addClass(bootstrap(bootstrapClass));
        }
        return button;
    }
    
    public enum TYPE
    {
        PRIMARY, SECONDARY, SUCCESS, DANGER, WARNING, INFO, LIGHT, DARK, LINK
    }
}
