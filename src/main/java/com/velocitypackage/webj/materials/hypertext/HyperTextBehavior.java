package com.velocitypackage.webj.materials.hypertext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class HyperTextBehavior
{
    private final List<HyperTextBehavior> children;
    private final Element content;
    private String id;
    private String cache;
    
    public HyperTextBehavior(Element element)
    {
        children = new ArrayList<>();
        content = element;
        id = this.toString();
        if (content instanceof HyperTextElement)
        {
            ((HyperTextElement) content).addAttribute(Attribute.ID, id);
        }
        cache = "";
    }
    
    public final void onMessage(String id, Map<String, String> values)
    {
        if (id.equals(this.id))
        {
            onInteract(values);
        } else
        {
            for (HyperTextBehavior h : children)
            {
                h.onMessage(id, values);
            }
        }
    }
    
    public final String build()
    {
        return cache;
    }
    
    public final void add(HyperTextBehavior hyperTextBehavior)
    {
        if (hyperTextBehavior != null)
        {
            children.add(hyperTextBehavior);
        }
    }
    
    public abstract void onInteract(Map<String, String> values);
}
