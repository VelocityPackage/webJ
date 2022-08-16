package com.velocitypackage.webJ.materials.webJ;

import com.velocitypackage.webJ.materials.hypertext.Bootstrap;
import com.velocitypackage.webJ.materials.hypertext.HyperTextPage;
import com.velocitypackage.webJ.materials.hypertext.Style;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * IMPORTANT -> constructor parameter always overwrite with null
 * @author maxmielchen
 */
@SuppressWarnings("unused")
public abstract class Application implements Cloneable
{
    private String applicationName;
    private final Set<HyperTextPage> pages = new HashSet<>();
    private String currentPath = "/";
    private File favicon;
    private Runnable onForceUpdate;
    
    /**
     * Adds a new Page
     * @param page the page
     */
    public final void addPage(HyperTextPage page)
    {
        this.pages.add(page);
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
     * @param favicon the application icon (only *.ico)
     * @throws IllegalArgumentException if the file is not a valid image or null
     */
    public final void setFavicon(File favicon) throws IllegalArgumentException
    {
        if (favicon == null)
        {
            throw new IllegalArgumentException("favicon cannot be null");
        }
        if (!(favicon.getName().endsWith(".ico") || favicon.getName().endsWith(".ico")))
        {
            throw new IllegalArgumentException("only ico is supported");
        }
        this.favicon = favicon;
    }
    
    
    /**
     * Sets the force update
     * @param runnable force update methode
     */
    public final void setForceUpdate(Runnable runnable)
    {
        this.onForceUpdate = runnable;
    }
    
    public final String getCurrentPageName()
    {
        for (HyperTextPage hyperTextPage : pages) {
            if (hyperTextPage.getPath().equals(currentPath)) {
                return hyperTextPage.getName();
            }
        }
        return "";
    }
    
    /**
     * Returns an interpretation of all components as html of the current page
     * @return string as html
     */
    public final String getCurrentPageRepresentation()
    {
        for (HyperTextPage hyperTextPage : pages) {
            if (hyperTextPage.getPath().equals(currentPath)) {
                return hyperTextPage.getTextRepresentation();
            }
        }
        return "<div class=\"text-center d-block vh-100 position-fixed vw-100\"><hr /><h3 class=\"text-center\">404</h3><p class=\"text-center\">This page doesn't exist...</p><hr /></div>";
    }
    
    /**
     * Returns an interpretation of the style options of the current page
     * @return the style options of the current page
     */
    public final Style[] getCurrentPageStyle()
    {
        for (HyperTextPage hyperTextPage : pages) {
            if (hyperTextPage.getPath().equals(currentPath)) {
                return hyperTextPage.getStyles();
            }
        }
        return new Style[]{};
    }
    
    /**
     * Returns an interpretation of the bootstrap options of the current page
     * @return the bootstrap options of the current page
     */
    public final Bootstrap[] getCurrentPageBootstraps()
    {
        for (HyperTextPage hyperTextPage : pages) {
            if (hyperTextPage.getPath().equals(currentPath)) {
                return hyperTextPage.getBootstraps();
            }
        }
        return new Bootstrap[]{};
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
    public final File getFavicon()
    {
        return favicon;
    }
    
    /**
     * get all pages
     * @return array of pages
     */
    public final HyperTextPage[] getPages()
    {
        HyperTextPage[] pages = new HyperTextPage[this.pages.size()];
        this.pages.toArray(pages);
        return pages;
    }
    
    /**
     * update the UI
     */
    public final void forceUpdate()
    {
        if (onForceUpdate == null)
        {
            return;
        }
        onForceUpdate.run(); //force update
    }
    
    /**
     * onMessage
     * @param message the message as string
     */
    public final void onMessage(String message) throws NotSupportedMessageFormatException
    {
        if (message.trim().startsWith("path:")) {
            currentPath = message.replaceAll("path:", "").trim();
            return;
        }
        //id:<id> inputs:{}
        //id:<id> inputs:{<inputID>??<value>;;...}
        try
        {
            String id = message.split(" ", 2)[0].trim().replaceAll("id:", "").trim();
            String[] inputs = message.split(" ", 2)[1].trim().replaceAll("inputs:\\{", "").replaceAll("}", "").split(";;");
            Map<String, String> inputMap = new HashMap<>();
            if (message.contains("??"))
            {
                for (String s : inputs)
                {
                    inputMap.put(s.split("\\?\\?")[0], s.split("\\?\\?")[1]);
                }
            }
            pages.forEach(hyperTextPage -> {
                if (hyperTextPage.getPath().equals(currentPath)) {
                    hyperTextPage.onMessage(id, inputMap);
                }
            });
        } catch (Exception ignore)
        {
            throw new NotSupportedMessageFormatException("id:<id> inputs:{}", "id:<id> inputs:{<inputID>??<value>;;...}");
        }
    }
    
    @Override
    public final Application clone() throws CloneNotSupportedException
    {
        try
        {
            return this.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            throw new CloneNotSupportedException("Async Application Clients isn't working. The ground can be that the application constructor isn't public.");
        }
    }
}
