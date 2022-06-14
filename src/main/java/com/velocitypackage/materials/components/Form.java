package com.velocitypackage.materials.components;

import java.util.Map;
import java.util.Objects;

public abstract class Form extends Component
{
    private final String id;
    
    public Form()
    {
        id = String.valueOf(this.hashCode());
    }
    
    @Override
    public void add(Component component)
    {
        components.add(component);
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
        for (Bootstrap bootstrapClass : classes)
        {
            form.addClass(bootstrap(bootstrapClass));
        }
        return form;
    }
}
