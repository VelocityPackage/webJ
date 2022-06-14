package com.velocitypackage.materials.components;

import java.util.Map;

public class FlexGrid extends Component
{
    public FlexGrid()
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
        for (Bootstrap bootstrapClass : classes)
        {
            container.addClass(bootstrap(bootstrapClass));
        }
        return container;
    }
}
