package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View implements Component
{
    private final List<Component> components;
    private final List<Button> buttons;
    
    private final Map<HyperTextElement.STYLE, String> styles;
    
    public View()
    {
        components = new ArrayList<>();
        buttons = new ArrayList<>();
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
    public void onInteract(String id, Map<String, String> inputs)
    {
        for (Component component : components)
        {
            component.onInteract(id, inputs);
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
        container.addClass("container");
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            container.addStyle(style.getKey(), style.getValue());
        }
        return container;
    }
}
