package com.velocitypackage.webj.materials.hypertext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Each HyperTextBehavior stores a HyperTextElement object and is responsible for generating its text representation and combining it with the text representation of its children.
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
    public String id;
    private String cache;
    
    /**
     * A constructor for the HyperTextBehavior class that creates a new HyperTextBehavior object.
     */
    public HyperTextBehavior()
    {
        children = new ArrayList<>();
        hyperTextElement = new HyperTextElement("");
        id = hyperTextElement.getId();
        cache = "";
    }
    
    /**
     * Assigns the given HyperTextElement object to this HyperTextBehavior object.
     *
     * @param hyperTextElement the new HyperTextElement object of this HyperTextBehavior object
     */
    public void setHyperTextElement(HyperTextElement hyperTextElement)
    {
        this.hyperTextElement = hyperTextElement;
        this.id = hyperTextElement.getId();
        recompile();
    }
    
    /**
     * Recompiles the text representation of this HyperTextBehavior and stores it in the cache.
     */
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
    
    /**
     * Should be invoked when an interaction on this behavior or one of its children occurred.
     * This method will either call the onInteract method of this behavior or forward the interaction to all its children depending on the id of the event message.
     *
     * @param id     the id of behavior this interaction should be relayed to
     * @param values the values that got sent with the interaction event
     */
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
    
    /**
     * Gets the text representation of this behavior combined with the text representation of its children.
     *
     * @return the text representation of this behavior combined with the text representation of its children
     */
    public final String getTextRepresentation()
    {
        return cache;
    }
    
    /**
     * Adds a child behavior to this behavior.
     * This corresponds to the creation of nested html where this behavior is responsible for the outer html tag and the new child forms a new inner html tag.
     *
     * @param hyperTextBehavior the behavior that gets added to the child list of this behavior
     */
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
    
    /**
     * Removes the specified behavior from the child list of this behavior if present.
     *
     * @param hyperTextBehavior the behavior that should be removed from the child list odf this behavior
     */
    @SuppressWarnings("unused")
    public final void removeChild(HyperTextBehavior hyperTextBehavior)
    {
        if (hyperTextBehavior != null)
        {
            children.remove(hyperTextBehavior);
        }
        recompile();
    }
    
    /**
     * Is invoked when this behavior receives an interaction.
     *
     * @param values the values that belong to the interaction event
     */
    public abstract void onInteract(Map<String, String> values);
    
    /**
     * Sets the parent object of this behavior to the specified behavior.
     * Should only be called from the parent object itself when it is adding a child to its list.
     * This is important for the recompilation process because a child object has to inform its parent when something changed and the text representation has to be updated.
     *
     * @param hyperTextBehavior the behavior that is the new parent object
     */
    private void setParent(HyperTextBehavior hyperTextBehavior)
    {
        parent = hyperTextBehavior;
    }
}
