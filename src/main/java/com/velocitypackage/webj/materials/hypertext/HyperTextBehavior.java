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
    private HyperTextBehavior parent;
    private Element content;
    private String id;
    private String cache;
    
    public HyperTextBehavior()
    {
        children = new ArrayList<>();
        content = new TextElement("");
        id = null;
        cache = "";
    }
    
    public final void setContent(Element element)
    {
        content = element;
        if (element instanceof HyperTextElement)
        {
            id = ((HyperTextElement) element).getId();
        }
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
            stringBuilder.append("<").append(((HyperTextElement) content).getTag());
            stringBuilder.append(" ");
            stringBuilder.append(((HyperTextElement) content).getAttributes());
            stringBuilder.append(" >");
            for (HyperTextBehavior hyperTextBehavior : children)
            {
                stringBuilder.append(hyperTextBehavior.build());
            }
            stringBuilder.append("</").append(((HyperTextElement) content).getTag()).append(">");
            cache = stringBuilder.toString();
        }
        if (parent != null)
        {
            parent.recompile();
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
    
    @SuppressWarnings("unused")
    public final void addChild(HyperTextBehavior hyperTextBehavior)
    {
        if (hyperTextBehavior != null)
        {
            children.add(hyperTextBehavior);
            hyperTextBehavior.setParent(this);
        }
        recompile();
    }
    
    @SuppressWarnings("unused")
    public final void removeChild(HyperTextBehavior hyperTextBehavior)
    {
        if (hyperTextBehavior != null)
        {
            children.remove(hyperTextBehavior);
        }
        recompile();
    }
    
    public abstract void onInteract(Map<String, String> values);
    
    public void setParent(HyperTextBehavior hyperTextBehavior)
    {
        parent = hyperTextBehavior;
    }
}
