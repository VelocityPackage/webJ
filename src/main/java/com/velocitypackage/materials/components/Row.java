package com.velocitypackage.materials.components;

import java.util.Map;

public class Row extends Component
{
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
        HyperTextElement row = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
        row.addClass("d-flex", "flex-nowrap");
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            row.addStyle(style.getKey(), style.getValue());
        }
        for (Bootstrap bootstrapClass : classes)
        {
            row.addClass(bootstrap(bootstrapClass));
        }
        return row;
    }
}
