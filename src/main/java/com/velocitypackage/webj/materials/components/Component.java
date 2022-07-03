package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;

import java.util.Map;

/**
 * @author maxmielchen
 */
public abstract class Component extends HyperTextBehavior
{
    /**
     * Creates a component out of a hypertext element
     * @param hyperTextElement hyper text element
     * @return return the new instance of a component
     */
    public static Component fromElement(HyperTextElement hyperTextElement)
    {
        Component component = new Component()
        {
            @Override
            public void onInteract(Map<String, String> values)
            {
            }
        };
        component.setHyperTextElement(hyperTextElement);
        return component;
    }
    
    /**
     * Combines two style arrays
     * @param styles1 style array 1
     * @param styles2 style  array 2
     * @return combined version of this two arrays
     */
    public static Style[] combine(Style[] styles1, Style[] styles2)
    {
        Style[] combine = new Style[styles1.length + styles2.length];
        System.arraycopy(styles1, 0, combine, 0, styles1.length);
        System.arraycopy(styles2, 0, combine, styles1.length, styles2.length);
        return combine;
    }
    
    /**
     * Combines two bootstrap arrays
     * @param bootstraps1 bootstrap array 1
     * @param bootstraps2 bootstrap  array 2
     * @return combined version of this two arrays
     */
    public static Bootstrap[] combine(Bootstrap[] bootstraps1, Bootstrap[] bootstraps2)
    {
        Bootstrap[] combine = new Bootstrap[bootstraps1.length + bootstraps2.length];
        System.arraycopy(bootstraps1, 0, combine, 0, bootstraps1.length);
        System.arraycopy(bootstraps2, 0, combine, bootstraps1.length, bootstraps2.length);
        return combine;
    }
}
