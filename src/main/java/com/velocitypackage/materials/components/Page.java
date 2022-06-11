package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page
{
    private final List<Component> components;
    private final List<Button> buttons;
    
    public Page()
    {
        components = Collections.synchronizedList(new ArrayList<>());
        buttons = Collections.synchronizedList(new ArrayList<>());
    }
    
    public void add(Component component)
    {
        components.add(component);
        if (component instanceof Button)
        {
            buttons.add((Button) component);
        }
    }
    
    public void onClick(String id)
    {
        for (Component component : components)
        {
            component.onClick(id);
        }
        
        for (Button button : buttons)
        {
            if (button.id.trim().equals(id.trim()))
            {
                button.onClick();
            }
        }
    }
    
    public String getHyperText()
    {
        StringBuilder content = new StringBuilder();
        for (Component component : components)
        {
            content.append(component.getHTML().compile());
        }
        return new String(content);
    }
}
