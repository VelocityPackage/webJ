package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Header implements Component
{
    private final List<Item> items;
    
    public Header()
    {
        items = new ArrayList<>();
    }
    
    @Override
    public void add(Component component)
    {
        if (component instanceof Item)
        {
            items.add((Item) component);
        }
    }
    
    @Override
    public void putStyle(HyperTextElement.STYLE option, String value)
    {
    }
    
    @Override
    public void onInteract(String id, Map<String, String> inputs)
    {
        for (Item item : items)
        {
            item.onInteract(id, inputs);
        }
    }
    
    @Override
    public HyperTextElement getContent()
    {
        StringBuilder content = new StringBuilder();
        for (Item item : items)
        {
            content.append(item.getContent().compile());
        }
        HyperTextElement ul = new HyperTextElement(HyperTextElement.TAG.UL, new String(content));
        ul.addClass("nav", "nav-pills");
        HyperTextElement header = new HyperTextElement(HyperTextElement.TAG.HEADER, ul.compile());
        header.addClass("d-flex", "justify-content-center", "py-3");
        HyperTextElement container = new HyperTextElement(HyperTextElement.TAG.DIV, header.compile());
        container.addClass("container");
        HyperTextElement aContainer = new HyperTextElement(HyperTextElement.TAG.DIV, container.compile());
        aContainer.addStyle(HyperTextElement.STYLE.BOX_SHADOW, "0px 0px 7px 1px rgb(0 0 0 / 15%)");
        aContainer.addStyle(HyperTextElement.STYLE.MARGIN_BOTTOM, "17px");
        return aContainer;
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
        public void putStyle(HyperTextElement.STYLE style, String option)
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
                HyperTextElement item = new HyperTextElement(HyperTextElement.TAG.LI, "<button class=\"nav-link active\" id=\"" + id + "\" aria-current=\"page\">" + name + "</button>");
                item.addClass("nav-item");
                return item;
            }
            HyperTextElement item = new HyperTextElement(HyperTextElement.TAG.LI, "<button class=\"nav-link\" id=\"" + id + "\" aria-current=\"page\">" + name + "</button>");
            item.addClass("nav-item");
            return item;
        }
    }
}
