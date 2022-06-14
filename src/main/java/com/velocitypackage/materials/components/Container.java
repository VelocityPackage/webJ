package com.velocitypackage.materials.components;

import java.util.Map;

public class Container extends Component
{
    
    public Container()
    {
    }
    
    @Override
    public void add(Component component)
    {
        components.add(component);
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
        for (Bootstrap bootstrap : classes)
        {
            container.addClass(bootstrap.name().toLowerCase().replaceAll("_", "-"));
        }
        return container;
    }
}
