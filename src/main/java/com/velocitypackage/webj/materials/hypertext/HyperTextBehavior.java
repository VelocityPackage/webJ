package com.velocitypackage.webj.materials.hypertext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Each instance of the HyperTextBehavior stores a HyperTextElement object and is responsible for generating its text representation and combining it with the text representation of its children.
 * In addition to that a HyperTextBehavior object is responsible for managing the relation of its HyperTextElement object to its parent and child objects in the html structure.
 * When an event occurs the HyperTextBehavior object will either execute its onInteract method or forward the event to its children.
 * To minimize the time it takes to get the text representation each HyperTextBehavior object stores the compiled text representation in a cache and will only recompile it when necessary.
 *
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
            stringBuilder.append(hyperTextBehavior.getTextRepresentation());
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
    
    public final String getTextRepresentation()
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
