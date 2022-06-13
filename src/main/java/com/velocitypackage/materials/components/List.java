package com.velocitypackage.materials.components;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class List implements Component
{
    private final java.util.List<List.Item> items;
    private final Theme theme;
    
    public List(@NotNull Theme theme)
    {
        items = new ArrayList<>();
        this.theme = theme;
    }
    
    @Override
    public void add(Component component)
    {
        if (component instanceof List.Item)
        {
            items.add((List.Item) component);
        }
    }
    
    @Override
    public void putStyle(HyperTextElement.STYLE option, String value)
    {
    }
    
    @Override
    public void onInteract(String id, Map<String, String> inputs)
    {
        for (List.Item item : items)
        {
            item.onInteract(id, inputs);
        }
    }
    
    @Override
    public HyperTextElement getContent()
    {
        StringBuilder content = new StringBuilder();
        for (List.Item item : items)
        {
            content.append(item.getContent().compile());
        }
        HyperTextElement ul = new HyperTextElement(HyperTextElement.TAG.UL, new String(content));
        if (theme == Theme.LIGHT)
        {
            ul.addClass("dropdown-menu", "position-static", "d-grid", "gap-1", "p-2", "rounded-3", "mx-0", "shadow", "w-220px");
        }
        if (theme == Theme.DARK)
        {
            ul.addClass("dropdown-menu", "dropdown-menu-dark", "position-static", "d-grid", "gap-1", "p-2", "rounded-3", "mx-0", "border-0", "shadow", "w-220px");
        }
        ul.addStyle(HyperTextElement.STYLE.MARGIN, "10px");
        return ul;
    }
    
    public abstract static class Item implements Component
    {
        public final String name;
        private final String id;
        public final boolean active;
        
        public Item(String name, boolean active)
        {
            this.name = name;
            this.id = String.valueOf(this.hashCode());
            this.active = active;
        }
        
        @Override
        public void add(Component component)
        {
        }
        
        @Override
        public void putStyle(HyperTextElement.STYLE option, String value)
        {
        }
        
        @Override
        public void onInteract(String id, Map<String, String> inputs)
        {
            if (Objects.equals(this.id, id))
            {
                onClick();
            }
        }
        
        public abstract void onClick();
        
        @Override
        public HyperTextElement getContent()
        {
            if (active)
            {
                return new HyperTextElement(HyperTextElement.TAG.LI, "<button class=\"dropdown-item rounded-2 active\" id=\"" + id + "\" aria-current=\"page\">" + name + "</button>");
            }
            return new HyperTextElement(HyperTextElement.TAG.LI, "<button class=\"dropdown-item rounded-2\" id=\"" + id + "\" aria-current=\"page\">" + name + "</button>");
        }
    }
}
