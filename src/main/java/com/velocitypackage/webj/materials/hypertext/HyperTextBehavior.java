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
        if (content instanceof TextElement)
        {
            return ((TextElement) content).getText();
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
                stringBuilder.append(" ").append(hyperTextBehavior.build());
            }
            stringBuilder.append("</").append(((HyperTextElement) content).getTag().name().toLowerCase()).append(">");
            return stringBuilder.toString();
        } else
        {
            return null;
        }
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
