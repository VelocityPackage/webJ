package com.velocitypackage.materials.components;

import java.util.Map;

public class ButtonGroup extends Component
{
    
    public ButtonGroup()
    {
    }
    
    @Override
    public void add(Component component)
    {
        if (component instanceof Button)
        {
            components.add(component);
        }
    }
    
    @Override
    public HyperTextElement getContent()
    {
        StringBuilder content = new StringBuilder();
        for (Component component : components)
        {
            Button button = (Button) component;
            button.putStyle(HyperTextElement.STYLE.MARGIN, "0");
            content.append(button.getContent());
        }
        HyperTextElement buttonGroup = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
        buttonGroup.addStyle(HyperTextElement.STYLE.MARGIN, "10px");
        buttonGroup.addClass("btn-group");
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            buttonGroup.addStyle(style.getKey(), style.getValue());
        }
        for (Bootstrap bootstrapClass : classes)
        {
            buttonGroup.addClass(bootstrap(bootstrapClass));
        }
        return buttonGroup;
    }
}
