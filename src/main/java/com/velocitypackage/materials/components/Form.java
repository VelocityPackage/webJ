package com.velocitypackage.materials.components;

import java.util.List;
import java.util.*;

public abstract class Form implements Component
{
    private final List<Component> components;
    private final Map<HyperTextElement.STYLE, String> styles;
    private final String id;
    
    public Form()
    {
        components = new ArrayList<>();
        styles = new HashMap<>();
        id = super.toString();
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
        if (Objects.equals(this.id.trim(), id.trim()))
        {
            callback(inputs);
        }
    }
    
    public String getInputKey(Input input)
    {
        return input.getFormId();
    }
    
    public abstract void callback(Map<String, String> data);
    
    @Override
    public HyperTextElement getContent()
    {
        StringBuilder content = new StringBuilder();
        for (Component component : components)
        {
            content.append(component.getContent().compile());
        }
        HyperTextElement form = new HyperTextElement(HyperTextElement.TAG.FORM, new String(content));
        form.addId(id);
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            form.addStyle(style.getKey(), style.getValue());
        }
        return form;
    }
}
