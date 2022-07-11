package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;

import java.lang.reflect.Array;
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
     * Creates a component out of a hypertext element
     * @param hyperTextElement hyper text element
     * @return return the new instance of a component
     */
    public static Component fromElement(HyperTextElement hyperTextElement, Runnable interaction)
    {
        Component component = new Component()
        {
            @Override
            public void onInteract(Map<String, String> values)
            {
                interaction.run();
            }
        };
        component.setHyperTextElement(hyperTextElement);
        return component;
    }
    
    /**
     * Combines arrays
     * @return combined version of this arrays
     */
    @SafeVarargs
    public static <T> T[] combine(T[]... objectArrays)
    {
        @SuppressWarnings("unchecked")
        T[] objects = (T[]) new Object[]{};
        for (T[] objectArray : objectArrays)
        {
            objects = concatenate(objects, objectArray);
        }
        return objects;
    }
    
    private static  <T> T[] concatenate(T[] object1, T[] object2) {
        int object1_length = object1.length;
        int object2_length = object2.length;
        @SuppressWarnings("unchecked")
        T[] combined = (T[]) Array.newInstance(object1.getClass().getComponentType(), object1_length + object2_length);
        System.arraycopy(object1, 0, combined, 0, object1_length);
        System.arraycopy(object2, 0, combined, object1_length, object2_length);
        return combined;
    }
}
