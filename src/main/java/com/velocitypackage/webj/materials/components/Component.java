package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;

import java.util.Map;

public abstract class Component extends HyperTextBehavior
{
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
    
    public static Style[] combine(Style[] styles1, Style[] styles2)
    {
        Style[] combine = new Style[styles1.length + styles2.length];
        System.arraycopy(styles1, 0, combine, 0, styles1.length);
        System.arraycopy(styles2, 0, combine, styles1.length, styles2.length);
        return combine;
    }
    
    public static Bootstrap[] combine(Bootstrap[] bootstraps1, Bootstrap[] bootstraps2)
    {
        Bootstrap[] combine = new Bootstrap[bootstraps1.length + bootstraps2.length];
        System.arraycopy(bootstraps1, 0, combine, 0, bootstraps1.length);
        System.arraycopy(bootstraps2, 0, combine, bootstraps1.length, bootstraps2.length);
        return combine;
    }
}
