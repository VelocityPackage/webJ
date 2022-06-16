package com.velocitypackage.materials.components;

import com.velocitypackage.materials.hypertext.HyperTextElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author maxmielchen
 */
public abstract class Component
{
    private final List<Component> componentList = new ArrayList<>();
    
    public final void add(Component... component)
    {
        if (component == null || component.length == 0)
        {
            return;
        }
        componentList.addAll(List.of(component));
    }
    
    public final void remove(Component... component)
    {
        if (component == null || component.length == 0)
        {
            return;
        }
        componentList.removeAll(List.of(component));
    }
    
    public void onInteract(String id, Map<String, String> values)
    {
        if (componentList.isEmpty())
        {
            return;
        }
        for (Component component : componentList)
        {
            component.onInteract(id, values);
        }
    }
    
    public final HyperTextElement[] getChildren()
    {
        if (componentList.isEmpty())
        {
            return new HyperTextElement[]{};
        }
        List<HyperTextElement> hyperTextElementList = new ArrayList<>();
        for (Component component : componentList)
        {
            hyperTextElementList.add(component.getHyperText());
        }
        return hyperTextElementList.toArray(new HyperTextElement[0]);
    }
    
    public abstract HyperTextElement getHyperText();
}
