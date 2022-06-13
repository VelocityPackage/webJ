package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sidebar implements Component
{
    private final List<Component> components;
    private final List<Button> buttons;
    private final Map<HyperTextElement.STYLE, String> styles;
    private final Theme theme;
    
    public Sidebar(Theme theme, int width)
    {
        this.components = new ArrayList<>();
        this.buttons = new ArrayList<>();
        this.styles = new HashMap<>();
        this.theme = theme;
        styles.put(HyperTextElement.STYLE.WIDTH, width + "px");
        styles.put(HyperTextElement.STYLE.HEIGHT, "100vh");
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
            if (button.id.equals(id.trim()))
            {
                button.onClick();
            }
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
        HyperTextElement sidebar = new HyperTextElement(HyperTextElement.TAG.DIV);
        switch (theme)
        {
            case DARK:
                HyperTextElement dark = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
                dark.addClass("flex-shrink-0", "p-3", "bg-dark");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    dark.addStyle(style.getKey(), style.getValue());
                }
                sidebar = dark;
                break;
            case LIGHT:
                HyperTextElement light = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
                light.addClass("flex-shrink-0", "p-3", "bg-white");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    light.addStyle(style.getKey(), style.getValue());
                }
                sidebar = light;
                break;
        }
        return sidebar;
    }
}
