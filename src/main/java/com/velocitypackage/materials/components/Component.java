package com.velocitypackage.materials.components;

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
    
    /**
     * for button interaction
     *
     * @param id
     */
    void onClick(String id);
    
    /**
     * returns the compiled html
     *
     * @return html
     */
    HyperTextElement getHTML();
}
