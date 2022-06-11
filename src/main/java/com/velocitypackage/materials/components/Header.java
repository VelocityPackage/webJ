package com.velocitypackage.materials.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Header implements Component
{
    private final List<Item> items;
    
    public Header()
    {
        items = Collections.synchronizedList(new ArrayList<>());
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
    public void onClick(String id)
    {
        for (Item item : items)
        {
            item.onClick(id);
        }
    }
    
    @Override
    public HyperTextElement getHTML()
    {
        StringBuilder content = new StringBuilder();
        for (Item item : items)
        {
            content.append(item.getHTML().compile());
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
        public final String id;
        
        public Item(String name, String id)
        {
            this.name = name;
            this.id = id;
        }
        
        @Override
        public void add(Component component)
        {
        }
        
        @Override
        public void onClick(String id)
        {
            if (Objects.equals(this.id, id))
            {
                onClick();
            }
        }
        
        abstract void onClick();
        
        @Override
        public HyperTextElement getHTML()
        {
            HyperTextElement item = new HyperTextElement(HyperTextElement.TAG.LI, "<a href=\"#\" class=\"nav-link\" aria-current=\"page\">" + name + "</a>");
            item.addClass("nav-item");
            return item;
        }
    }
}
