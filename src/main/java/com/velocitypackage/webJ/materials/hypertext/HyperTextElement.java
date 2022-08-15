package com.velocitypackage.webJ.materials.hypertext;

import java.util.*;

/**
 * Each instance of the HyperTextElement class represents a html element. It can be a html tag with html attributes or a simple text.
 * Objects of the HyperTextElement class are immutable.
 *
 * @author marvinmielchen
 * @author maxmielchen
 */
public final class HyperTextElement
{
    private final Tag tag;
    private final List<Attribute> attributeList;
    private final String id;
    private final String text;
    
    /**
     * A constructor for the HyperTextElement class that creates a new HyperTextElement object from the given tag, bootstrap classes, html attributes and style properties.
     *
     * @param tag        the tag of the new HyperTextElement object
     * @param bootstraps the bootstrap classes of the new HyperTextElement object
     * @param attributes the html attributes of the new HyperTextElement object
     * @param styles     the style properties of the new HyperTextElement object
     */
    public HyperTextElement(Tag tag, Bootstrap[] bootstraps, Attribute[] attributes, Style[] styles)
    {
        this.text = "";
        this.tag = tag;
        this.attributeList = new ArrayList<>();
        this.id = String.valueOf(this.hashCode());
        if (attributes != null)
        {
            for (Attribute a : attributes)
            {
                this.addAttribute(a);
            }
        }
        if (bootstraps != null)
        {
            for (Bootstrap b : bootstraps)
            {
                this.addAttribute(new Attribute(Attribute.AttributeIdentifier.CLASS, b.name().replaceAll("_", "-").toLowerCase()));
            }
        }
        if (styles != null)
        {
            for (Style s : styles)
            {
                this.addAttribute(new Attribute(Attribute.AttributeIdentifier.STYLE, s.getIdentifier().name().toLowerCase().replaceAll("_", "-") + ":" + s.getValue() + ";"));
            }
        }
        this.addAttribute(new Attribute(Attribute.AttributeIdentifier.ID, this.id));
    }
    
    /**
     * A constructor for the HyperTextElement class that creates a new HyperTextElement object from the given tag, bootstrap classes, html attributes and style properties.
     *
     * @param tag        the tag of the new HyperTextElement object
     * @param bootstraps the bootstrap classes of the new HyperTextElement object
     * @param attributes the html attributes of the new HyperTextElement object
     * @param styles     the style properties of the new HyperTextElement object
     * @param text       the text of the new HyperTextElement object
     */
    public HyperTextElement(Tag tag, Bootstrap[] bootstraps, Attribute[] attributes, Style[] styles, String text)
    {
        this.text = Objects.requireNonNullElse(text, "");
        this.tag = tag;
        this.attributeList = new ArrayList<>();
        this.id = String.valueOf(this.hashCode());
        if (attributes != null)
        {
            for (Attribute a : attributes)
            {
                this.addAttribute(a);
            }
        }
        if (bootstraps != null)
        {
            for (Bootstrap b : bootstraps)
            {
                this.addAttribute(new Attribute(Attribute.AttributeIdentifier.CLASS, b.name().replaceAll("_", "-").toLowerCase()));
            }
        }
        if (styles != null)
        {
            for (Style s : styles)
            {
                this.addAttribute(new Attribute(Attribute.AttributeIdentifier.STYLE, s.getIdentifier().name().toLowerCase().replaceAll("_", "-") + ":" + s.getValue() + ";"));
            }
        }
        this.addAttribute(new Attribute(Attribute.AttributeIdentifier.ID, this.id));
    }
    
    /**
     * A constructor for the HyperTextElement class that creates a new HyperTextElement object from the given text.
     *
     * @param text the text of the new HyperTextElement object
     */
    public HyperTextElement(String text)
    {
        this.text = text;
        this.tag = null;
        this.attributeList = new ArrayList<>();
        this.id = String.valueOf(this.hashCode());
    }
    
    /**
     * Adds the given html attribute to the attribute list of this HyperTextElement object.
     *
     * @param attribute the html attribute that is added to the attribute list of this HyperTextElement object
     */
    private void addAttribute(Attribute attribute)
    {
        if (attribute == null)
        {
            return;
        }
        if (! attributeList.contains(attribute))
        {
            attributeList.add(attribute);
        }
    }
    
    /**
     * Gets the text representation of the html tag that belongs to this HyperTextElement object.
     *
     * @return the text representation of the html tag that belongs to this HyperTextElement object
     */
    public String getTag()
    {
        if (tag == null)
        {
            return null;
        }
        return tag.name().replaceAll("_", "-").toLowerCase();
    }
    
    /**
     * Gets the text representation of all the attributes that belong to this HyperTextElement object.
     *
     * @return the text representation of all the attributes that belong to this HyperTextElement object
     */
    public String getAttributes()
    {
        Map<Attribute.AttributeIdentifier, String> map = new HashMap<>();
        for (Attribute a : attributeList)
        {
            if (! map.containsKey(a.getIdentifier()))
            {
                map.put(a.getIdentifier(), a.getValue());
            } else if (! map.containsValue(a.getValue()))
            {
                map.put(a.getIdentifier(), map.get(a.getIdentifier()) + " " + a.getValue());
            }
        }
        
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Attribute.AttributeIdentifier, String> a : map.entrySet())
        {
            stringBuilder.append(a.getKey().name().toLowerCase().replaceAll("_", "-"));
            if (a.getValue() != null && !a.getValue().equals(""))
            {
                stringBuilder.append("=\"");
                stringBuilder.append(a.getValue());
                stringBuilder.append("\" ");
            } else
            {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
    
    /**
     * Gets the unique id of this HyperTextElement object.
     * This is also stored in the "id" attribute of this HyperTextElement object and can be used to identify button clicks and similar events.
     *
     * @return the unique id of this HyperTextElement object
     */
    public String getId()
    {
        return this.id;
    }
    
    /**
     * Gets the text of this HyperTextElement object.
     * This method returns the content of the text variable of this HyperTextElement object
     * which should not be confused with the text representation as that will be generated later in the HyperTextBehavior that holds the reference to this object.
     *
     * @return the content of the text variable of this HyperTextElement object
     */
    public String getText()
    {
        return text;
    }
}
