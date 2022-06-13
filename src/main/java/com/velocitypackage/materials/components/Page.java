package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Page
{
    private final List<Component> components;
    
    public Page()
    {
        components = new ArrayList<>();
    }
    
    public void add(Component component)
    {
        components.add(component);
    }
    
    public void onInteract(String id, Map<String, String> inputs)
    {
        for (Component component : components)
        {
            component.onInteract(id, inputs);
        }
    }
    
    public String getHyperText()
    {
        StringBuilder content = new StringBuilder();
        for (Component component : components)
        {
            content.append(component.getContent().compile());
        }
        return new String(content);
    }
}
