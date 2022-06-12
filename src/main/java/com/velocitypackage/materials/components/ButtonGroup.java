package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.List;

public class ButtonGroup implements Component
{
    private final List<Button> buttons = new ArrayList<>();
    
    @Override
    public void add(Component component)
    {
        if (component instanceof Button)
        {
            buttons.add((Button) component);
        }
    }
    
    @Override
    public void putStyle(HyperTextElement.STYLE option, String value)
    {
    }
    
    @Override
    public void onClick(String id)
    {
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
        for (Button button : buttons)
        {
            button.putStyle(HyperTextElement.STYLE.MARGIN, "0");
            content.append(button.getContent());
        }
        HyperTextElement buttonGroup = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
        buttonGroup.addStyle(HyperTextElement.STYLE.MARGIN, "10px");
        buttonGroup.addClass("btn-group");
        return buttonGroup;
    }
}
