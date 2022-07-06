package com.velocitypackage.webj.materials.webJ;

import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * IMPORTANT -> constructor parameter always overwrite with null
 * @author maxmielchen
 */
public abstract class Application implements Cloneable
{
    private HyperTextBehavior hyperTextBehavior;
    private String applicationName;
    private File favicon;
    
    /**
     * sets the current page main
     * @param behavior the page main
     */
    public final void setRootBehavior(HyperTextBehavior behavior)
    {
        this.hyperTextBehavior = behavior;
    }
    
    /**
     * sets the application name
     * @param name the application name
     */
    public final void setApplicationName(String name)
    {
        this.applicationName = name;
    }
    
    /**
     * sets the application icon
     * @param favicon the application icon
     */
    public void setFavicon(File favicon)
    {
        this.favicon = favicon;
    }
    
    /**
     * Returns an interpretation of all components as html
     * @return string as html
     */
    public final String getTextRepresentation()
    {
        return this.hyperTextBehavior.getTextRepresentation();
    }
    
    /**
     * returns the application name
     * @return application name
     */
    public final String getApplicationName()
    {
        return this.applicationName;
    }
    
    /**
     * returns the application favicon
     * @return application favicon
     */
    public File getFavicon()
    {
        return favicon;
    }
    
    /**
     * onMessage
     * @param message the message as string
     */
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
