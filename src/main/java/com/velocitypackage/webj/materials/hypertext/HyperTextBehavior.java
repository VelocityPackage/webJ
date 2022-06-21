package com.velocitypackage.webj.materials.hypertext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author marvinmielchen
 */
public abstract class HyperTextBehavior
{
    private final List<HyperTextBehavior> children;
    private HyperTextBehavior parent;
    private HyperTextElement hyperTextElement;
    private String id;
    private String cache;
    
    public HyperTextBehavior()
    {
        children = new ArrayList<>();
        hyperTextElement = new HyperTextElement("");
        id = hyperTextElement.getId();
        cache = "";
    }
    
    public final void setHyperTextElement(HyperTextElement hyperTextElement)
    {
        this.hyperTextElement = hyperTextElement;
        this.id = hyperTextElement.getId();
        recompile();
    }
    
    public final void recompile()
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (hyperTextElement.getTag() != null)
        {
            stringBuilder.append("<").append(hyperTextElement.getTag()).append(" ").append(hyperTextElement.getAttributes()).append(">");
        }
        stringBuilder.append(hyperTextElement.getText());
        for (HyperTextBehavior hyperTextBehavior : children)
        {
            stringBuilder.append(hyperTextBehavior.build());
        }
        stringBuilder.append("</").append(hyperTextElement.getTag()).append(">");
        cache = stringBuilder.toString();
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
