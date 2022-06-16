package com.velocitypackage.materials.application;

import com.velocitypackage.materials.components.Component;
import com.velocitypackage.materials.hypertext.HyperTextElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AppRoot
{
    private Component currentRootComponent;
    
    public AppRoot()
    {
        currentRootComponent = new Component()
        {
            @Override
            public HyperTextElement getHyperText()
            {
                return new HyperTextElement("");
            }
        };
    }
    
    public void onMessage(String msg)
    {
        String[] args = msg.trim().split(" ", 2);
        String id = args[0].split(":")[1];
        String[] inputs = args[1].split(":", 2)[1].replaceFirst("\\{", "").split(";#;");
        inputs = Arrays.copyOf(inputs, inputs.length - 1); //cleanup
        Map<String, String> inputMap = new HashMap<>(); //id,value
        for (String input : inputs)
        {
            inputMap.put(input.split("\\?#\\?")[0], input.split("\\?#\\?")[1]);
        }
        currentRootComponent.onInteract(id, inputMap);
    }
    
    public void setPage(Component component)
    {
        currentRootComponent = component;
    }
    
    public HyperTextElement getHyperText()
    {
        return currentRootComponent.getHyperText();
    }
}
