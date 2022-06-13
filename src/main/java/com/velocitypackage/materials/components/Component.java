package com.velocitypackage.materials.components;

import java.util.Map;

/**
 * This interface is the first layer for the Component system
 *
 * @author maxmielchen
 */
public interface Component
{
    /**
     * adds some component
     *
     * @param component compo. self
     */
    void add(Component component);
    
    void putStyle(HyperTextElement.STYLE option, String value);
    
    /**
     * for interaction
     *
     * @param id
     */
    void onInteract(String id, Map<String, String> inputs);
    
    /**
     * returns the compiled html
     *
     * @return html
     */
    HyperTextElement getContent();
}
