package com.velocitypackage.materials.comonents;

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
    String getHTML();
}
