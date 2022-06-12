package com.velocitypackage.materials.components;

import java.util.List;
import java.util.*;

public class Container implements Component
{
    private final List<Component> components;
    private final List<Button> buttons;
    
    private final Map<HyperTextElement.STYLE, String> styles;
    
    public Container()
    {
        components = Collections.synchronizedList(new ArrayList<>());
        buttons = Collections.synchronizedList(new ArrayList<>());
        styles = new HashMap<>();
    }
    
    @Override
    public void add(Component component)
    {
        components.add(component);
        if (component instanceof Button)
        {
            buttons.add((Button) component);
        }
    }
    
    @Override
    public void putStyle(HyperTextElement.STYLE option, String value)
    {
        styles.put(option, value);
    }
    
    @Override
    public void onClick(String id)
    {
        for (Component component : components)
        {
            component.onClick(id);
        }
        for (Button button : buttons)
        {
            button.onClick();
        }
    }
    
    @Override
    public HyperTextElement getContent()
    {
        StringBuilder content = new StringBuilder();
        for (Component component : components)
        {
            content.append(component.getContent().compile());
        }
        HyperTextElement container = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
        
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            container.addStyle(style.getKey(), style.getValue());
        }
        return container;
    }
}
