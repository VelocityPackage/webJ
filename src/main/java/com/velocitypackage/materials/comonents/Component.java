package com.velocitypackage.materials.comonents;

/**
 * This interface is the first layer for the Component system
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
     * returns the compiled html
     *
     * @return html
     */
    HyperTextElement getHTML();
}
