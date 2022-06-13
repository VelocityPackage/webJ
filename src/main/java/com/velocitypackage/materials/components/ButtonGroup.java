package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ButtonGroup implements Component
{
    private final List<Button> buttons;
    private final Map<HyperTextElement.STYLE, String> styles;
    
    public ButtonGroup()
    {
        this.buttons = new ArrayList<>();
        this.styles = new HashMap<>();
    }
    
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
        styles.put(option, value);
    }
    
    @Override
    public void onInteract(String id, Map<String, String> inputs)
    {
        for (Button button : buttons)
        {
            button.onInteract(id, inputs);
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
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            buttonGroup.addStyle(style.getKey(), style.getValue());
        }
        return buttonGroup;
    }
}
