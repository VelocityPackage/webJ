package com.velocitypackage.webj.materials.components.forms;

import java.util.Map;

/**
 * @author maxmielchen
 */
public abstract class FormEvent
{
    public Map<String, String> values;
    
    /**
     * runs on a submit execution
     */
    public abstract void run();
    
    /**
     * Get the values of an Input
     * @param input input
     * @return value
     */
    public final String getInput(Input input)
    {
        return values.get(input.id);
    }
}
