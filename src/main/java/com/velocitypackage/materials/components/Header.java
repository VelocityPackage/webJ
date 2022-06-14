package com.velocitypackage.materials.components;

import java.util.Map;
import java.util.Objects;

public class Header extends Component
{
    public Header()
    {
    }
    
    @Override
    public void add(Component component)
    {
        if (component instanceof Item)
        {
            components.add(component);
        }
    }
    
    @Override
    public void onInteract(String id, Map<String, String> inputs)
    {
        for (Component component : components)
        {
            component.onInteract(id, inputs);
        }
    }
    
    @Override
    public HyperTextElement getContent()
    {
        StringBuilder content = new StringBuilder();
        for (Component component : components)
        {
            Item item = (Item) component;
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
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            aContainer.addStyle(style.getKey(), style.getValue());
        }
        for (Bootstrap bootstrapClass : classes)
        {
            aContainer.addClass(bootstrap(bootstrapClass));
        }
        return aContainer;
    }
    
    public abstract static class Item extends Component
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
            for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
            {
                item.addStyle(style.getKey(), style.getValue());
            }
            for (Bootstrap bootstrapClass : classes)
            {
                item.addClass(bootstrap(bootstrapClass));
            }
            return item;
        }
    }
}
