package com.velocitypackage.webJ.materials.hypertext;

import java.util.*;

/**
 * Each HyperTextBehavior stores a HyperTextElement object and is responsible for generating its text representation and combining it with the text representation of its children.
 * In addition to that a HyperTextBehavior object is responsible for managing the relation of its HyperTextElement object to its parent and child objects in the html structure.
 * When an event occurs the HyperTextBehavior object will either execute its onInteract method or forward the event to its children.
 * To minimize the time it takes to get the text representation each HyperTextBehavior object stores the compiled text representation in a cache and will only recompile it when necessary.
 *
 * @author marvinmielchen
 */
public abstract class HyperTextBehavior implements Collection<HyperTextBehavior>
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
    
    @Override
    public int size()
    {
        return children.size();
    }
    
    @Override
    public boolean isEmpty()
    {
        return children.isEmpty();
    }
    
    @Override
    public boolean contains(Object o)
    {
        return children.contains(o);
    }
    
    @Override
    public Iterator<HyperTextBehavior> iterator()
    {
        return children.iterator();
    }
    
    @Override
    public Object[] toArray()
    {
        return children.toArray(new HyperTextBehavior[0]);
    }
    
    @Override
    public <T> T[] toArray(T[] a)
    {
        return children.toArray(a);
    }
    
    @Override
    public boolean add(HyperTextBehavior hyperTextBehavior)
    {
        return children.add(hyperTextBehavior);
    }
    
    @Override
    public boolean remove(Object o)
    {
        return children.remove(o);
    }
    
    @Override
    public boolean containsAll(Collection<?> c)
    {
        return new HashSet<>(children).containsAll(c);
    }
    
    @Override
    public boolean addAll(Collection<? extends HyperTextBehavior> c)
    {
        return children.addAll(c);
    }
    
    @Override
    public boolean removeAll(Collection<?> c)
    {
        return children.removeAll(c);
    }
    
    @Override
    public boolean retainAll(Collection<?> c)
    {
        return children.retainAll(c);
    }
    
    @Override
    public void clear()
    {
        children.clear();
    }
}
