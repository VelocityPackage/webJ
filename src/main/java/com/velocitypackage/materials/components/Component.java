package com.velocitypackage.materials.components;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.*;

/**
 * This interface is the first layer for the Component system
 *
 * @author maxmielchen
 */
public abstract class Component
{
    public final java.util.List<Component> components = new ArrayList<>();
    public final Map<HyperTextElement.STYLE, String> styles = new HashMap<>();
    public final Set<Bootstrap> classes = new HashSet<>();
    
    /**
     * adds some components
     *
     * @param components components
     */
    public final void add(Component... components)
    {
        for (Component component : components)
        {
            add(component);
        }
    }
    
    /**
     * add a component
     *
     * @param component compo. self
     */
    public abstract void add(Component component);
    
    /**
     * puts some style
     *
     * @param option style option
     * @param value  value option as String
     */
    public final void putStyle(HyperTextElement.STYLE option, String value)
    {
        styles.put(option, value);
    }
    
    /**
     * puts some style
     *
     * @param option style option
     * @param value  value option as int
     */
    public final void putStyle(HyperTextElement.STYLE option, int value)
    {
        styles.put(option, String.valueOf(value));
    }
    
    /**
     * puts some class
     */
    public final void putBootstrap(Bootstrap... bootstrapClass)
    {
        classes.addAll(List.of(bootstrapClass));
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
     * @param id id
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
