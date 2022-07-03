package com.velocitypackage.webj.materials.webJ;

import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Application implements Cloneable
{
    private HyperTextBehavior hyperTextBehavior;
    private String applicationName;
    
    public final void setRootBehavior(HyperTextBehavior behavior)
    {
        this.hyperTextBehavior = behavior;
    }
    
    public final void setApplicationName(String name)
    {
        this.applicationName = name;
    }
    
    public final String getTextRepresentation()
    {
        return this.hyperTextBehavior.getTextRepresentation();
    }
    
    public final String getApplicationName()
    {
        return this.applicationName;
    }
    
    public final void onMessage(String message)
    {
        //id:<id> inputs:{}
        //id:<id> inputs:{<inputID>??<value>;;...}
        try
        {
            String id = message.split(" ")[0].trim().replaceAll("id:", "").trim();
            String[] inputs = message.split(" ")[1].trim().replaceAll("inputs:\\{", "").replaceAll("}", "").split(";;");
            Map<String, String> inputMap = new HashMap<>();
            if (message.contains("??"))
            {
                for (String s : inputs)
                {
                    inputMap.put(s.split("\\?\\?")[0], s.split("\\?\\?")[1]);
                }
            }
            hyperTextBehavior.onMessage(id, inputMap);
        } catch (Exception ignore)
        {}
    }
    
    @Override
    public final Application clone() throws CloneNotSupportedException
    {
        try
        {
            return this.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            throw new CloneNotSupportedException();
        }
    }
}
