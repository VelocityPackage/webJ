package com.velocitypackage.materials.components;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This interface is the first layer for the Component system
 *
 * @author maxmielchen
 */
public abstract class Component
{
    public final java.util.List<Component> components = new ArrayList<>();
    public final Map<HyperTextElement.STYLE, String> styles = new HashMap<>();
    public final List<Bootstrap> classes = new ArrayList<>();
    
    /**
     * adds some component
     *
     * @param component compo. self
     */
    public abstract void add(Component component);
    
    /**
     * puts some style
     *
     * @param option style option
     * @param value  value option
     */
    public final void putStyle(HyperTextElement.STYLE option, String value)
    {
        styles.put(option, value);
    }
    
    /**
     * puts some class
     */
    public final void putBootstrap(Bootstrap bootstrapClass)
    {
        classes.add(bootstrapClass);
    }
    
    /**
     * for interaction
     *
     * @param id
     */
    public void onInteract(String id, Map<String, String> inputs)
    {
        for (Component component : components)
        {
            component.onInteract(id, inputs);
        }
    }
    
    /**
     * Bootstrap to String
     *
     * @param bootstrapClass class
     */
    public String bootstrap(@NotNull Bootstrap bootstrapClass)
    {
        return bootstrapClass.name().trim().toLowerCase().replaceAll("_", "-");
    }
    
    /**
     * returns the compiled html
     *
     * @return html
     */
    public abstract HyperTextElement getContent();
}
