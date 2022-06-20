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
    private Element content;
    private final String id;
    private String cache;
    
    public HyperTextBehavior()
    {
        children = new ArrayList<>();
        content = new TextElement("");
        id = String.valueOf(this.hashCode());
        if (content instanceof HyperTextElement)
        {
            ((HyperTextElement) content).addAttribute(Attribute.ID, id);
        }
        cache = "";
    }
    
    public final void setContent(Element element)
    {
        content = element;
        recompile();
    }
    
    public final void recompile()
    {
        if (content instanceof TextElement)
        {
            cache = ((TextElement) content).getText();
        }
        if (content instanceof HyperTextElement)
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<").append(((HyperTextElement) content).getTag().name().toLowerCase());
            stringBuilder.append(" ");
            for (Attribute a : ((HyperTextElement) content).getAttributeMap().keySet())
            {
                stringBuilder.append(a.name().toLowerCase());
                stringBuilder.append("=\"");
                stringBuilder.append(((HyperTextElement) content).getAttributeMap().get(a));
                stringBuilder.append("\" ");
            }
            stringBuilder.append(" >");
            for (HyperTextBehavior hyperTextBehavior : children)
            {
                stringBuilder.append(hyperTextBehavior.build());
            }
            stringBuilder.append("</").append(((HyperTextElement) content).getTag().name().toLowerCase()).append(">");
            cache = stringBuilder.toString();
        }
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
    
    public final void addChild(HyperTextBehavior hyperTextBehavior)
    {
        if (hyperTextBehavior != null)
        {
            children.add(hyperTextBehavior);
        }
        recompile();
    }
    
    public final void removeChild(HyperTextBehavior hyperTextBehavior)
    {
        if (hyperTextBehavior != null)
        {
            children.remove(hyperTextBehavior);
        }
        recompile();
    }
    
    public abstract void onInteract(Map<String, String> values);
}
