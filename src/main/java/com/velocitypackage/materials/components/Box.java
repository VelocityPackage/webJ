package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Box implements Component
{
    private final List<Component> components;
    private final Map<HyperTextElement.STYLE, String> styles;
    private final TILT tilt;
    
    public Box(TILT tilt)
    {
        this.tilt = tilt;
        components = new ArrayList<>();
        styles = new HashMap<>();
    }
    
    public Box(TILT tilt, ALIGN align)
    {
        this.tilt = tilt;
        components = new ArrayList<>();
        styles = new HashMap<>();
        putStyle(HyperTextElement.STYLE.FLOAT, align.name().toLowerCase());
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
            content.append(component.getContent().compile());
        }
        HyperTextElement box = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
        if (tilt == TILT.HORIZONTAL)
        {
            box.addClass("d-flex", "flex-nowrap");
        }
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            box.addStyle(style.getKey(), style.getValue());
        }
        return box;
    }
    
    public enum TILT
    {
        HORIZONTAL, VERTICAL
    }
    
    public enum ALIGN
    {
        CENTER, LEFT, RIGHT
    }
}
