package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Box implements Component
{
    private final List<Component> components;
    private final List<Button> buttons;
    private final Map<HyperTextElement.STYLE, String> styles;
    private final TILT tilt;
    
    public Box(TILT tilt)
    {
        this.tilt = tilt;
        components = new ArrayList<>();
        buttons = new ArrayList<>();
        styles = new HashMap<>();
    }
    
    public Box(TILT tilt, ALIGN align)
    {
        this.tilt = tilt;
        components = new ArrayList<>();
        buttons = new ArrayList<>();
        styles = new HashMap<>();
        putStyle(HyperTextElement.STYLE.FLOAT, align.name().toLowerCase());
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
        HyperTextElement box = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
        if (tilt == TILT.HORIZONTAL)
        {
            box.addClass("d-flex", "flex-nowrap");
        }
        return box;
    }
    
    private enum TILT
    {
        HORIZONTAL, VERTICAL
    }
    
    private enum ALIGN
    {
        CENTER, LEFT, RIGHT
    }
}
