package com.velocitypackage.materials.components;

import java.util.Map;

public class Sidebar extends Component
{
    private final Theme theme;
    
    public Sidebar(Theme theme, int width)
    {
        this.theme = theme;
        styles.put(HyperTextElement.STYLE.WIDTH, width + "px");
        styles.put(HyperTextElement.STYLE.HEIGHT, "100vh");
    }
    
    @Override
    public void add(Component component)
    {
        components.add(component);
    }
    
    @Override
    public HyperTextElement getContent()
    {
        StringBuilder content = new StringBuilder();
        for (Component component : components)
        {
            content.append(component.getContent().compile());
        }
        HyperTextElement sidebar = new HyperTextElement(HyperTextElement.TAG.DIV);
        switch (theme)
        {
            case DARK:
                HyperTextElement dark = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
                dark.addClass("flex-shrink-0", "p-3", "bg-dark");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    dark.addStyle(style.getKey(), style.getValue());
                }
                sidebar = dark;
                break;
            case LIGHT:
                HyperTextElement light = new HyperTextElement(HyperTextElement.TAG.DIV, new String(content));
                light.addClass("flex-shrink-0", "p-3", "bg-white");
                for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
                {
                    light.addStyle(style.getKey(), style.getValue());
                }
                sidebar = light;
                break;
        }
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            sidebar.addStyle(style.getKey(), style.getValue());
        }
        for (Bootstrap bootstrapClass : classes)
        {
            sidebar.addClass(bootstrap(bootstrapClass));
        }
        return sidebar;
    }
}
