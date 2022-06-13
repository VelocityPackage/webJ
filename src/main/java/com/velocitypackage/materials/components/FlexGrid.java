package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlexGrid implements Component
{
    private final List<Component> components;
    
    private final Map<HyperTextElement.STYLE, String> styles;
    
    public FlexGrid()
    {
        components = new ArrayList<>();
        styles = new HashMap<>();
    }
    
    @Override
    public void add(Component component)
    {
        components.add(component);
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
            HyperTextElement element = new HyperTextElement(HyperTextElement.TAG.DIV, component.getContent().compile());
            element.addClass("col");
            content.append(element);
        }
        HyperTextElement row = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
        row.addClass("row");
        HyperTextElement container = new HyperTextElement(HyperTextElement.TAG.DIV, row.compile());
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            container.addStyle(style.getKey(), style.getValue());
        }
        return container;
    }
}
