package com.velocitypackage.webj.materials.hypertext;

import java.text.MessageFormat;
import java.util.*;

/**
 * HyperTextElement is an HTML element, that can be modified
 *
 * @author maxmielchen
 * @author marvinmielchen
 */
@SuppressWarnings("unused")
public final class HyperTextElement
{
    private final Tag tag;
    private final List<HyperTextElement> contentList;
    private final Map<Attribute, String> attributeMap;
    private final String text;
    
    /**
     * Returns a new Object of HypertextElement.
     *
     * @param tag is the html tag with them, you define the HTML element like div, head body, img ...
     */
    public HyperTextElement(Tag tag)
    {
        this.tag = tag;
        this.contentList = new ArrayList<>();
        this.attributeMap = new HashMap<>();
        this.text = null;
    }
    
    /**
     * Return a new Object of HypertextElement, which generates only a String with => *.generate();
     *
     * @param text the string that comes on *.generate();
     */
    public HyperTextElement(String text)
    {
        if (text == null)
        {
            text = "";
        }
        this.tag = Tag.DIV;
        this.contentList = new ArrayList<>();
        this.attributeMap = new HashMap<>();
        this.text = text;
    }
    
    /**
     * Adds some child elements to this element or defines the content.
     *
     * @param hyperTextElement the child elements or content. (can't be null)
     */
    public void addContent(HyperTextElement... hyperTextElement)
    {
        if (hyperTextElement == null || hyperTextElement.length == 0)
        {
            return;
        }
        contentList.addAll(List.of(hyperTextElement));
    }
    
    /**
     * Remove some child elements on this element or content, when they exist.
     * It's only remove the elements with the same reference.
     *
     * @param hyperTextElement the child elements or content.
     * @throws NullPointerException throws when the reference of this element didn't exist in the parent element.
     */
    public void removeContent(HyperTextElement... hyperTextElement) throws NullPointerException
    {
        contentList.removeAll(List.of(hyperTextElement));
    }
    
    /**
     * Write some attributes to the elements and when the attribute exist appends it to the existing.
     *
     * @param attribute keyword of the attribute (can't be null)
     * @param value     and the attribute option (can be null)
     */
    public void addAttribute(Attribute attribute, String value)
    {
        if (attribute == null)
        {
            return;
        }
        if (value == null)
        {
            value = "";
        }
        if (! attributeMap.containsKey(attribute))
        {
            attributeMap.put(attribute, value);
            return;
        }
        if (! (attributeMap.get(attribute).contains(value)))
        {
            attributeMap.put(attribute, attributeMap.get(attribute) + " " + value);
        }
    }
    
    /**
     * Remove some attribute values to the element and not the attribute self.
     *
     * @param attribute keyword of the attribute (can't be null)
     * @param value     and the attribute option (can't be null)
     */
    public void removeAttribute(Attribute attribute, String value)
    {
        if (attribute == null)
        {
            return;
        }
        if (value == null || value.equals(""))
        {
            return;
        }
        if (attributeMap.containsKey(attribute))
        {
            attributeMap.put(attribute, attributeMap.get(attribute).replaceAll(value, " "));
        }
    }
    
    /**
     * Adds some classes as String to the element.
     *
     * @param classList (can't be null)
     */
    public void addClass(String... classList)
    {
        if (classList == null || classList.length == 0)
        {
            return;
        }
        for (String c : classList)
        {
            addAttribute(Attribute.CLASS, c);
        }
    }
    
    /**
     * Adds some classes as Bootstrap enum to the element.
     *
     * @param classList (can't be null)
     */
    public void addClass(Bootstrap... classList)
    {
        if (classList == null || classList.length == 0)
        {
            return;
        }
        for (Bootstrap c : classList)
        {
            addAttribute(Attribute.CLASS, c.name().toLowerCase().replaceAll("_", "-"));
        }
    }
    
    /**
     * Adds some ids as String to the element.
     *
     * @param idList (can't be null)
     */
    public void addId(String... idList)
    {
        if (idList == null || idList.length == 0)
        {
            return;
        }
        for (String id : idList)
        {
            addAttribute(Attribute.ID, id);
        }
    }
    
    /**
     * Add a style option to the element or overwrite a style option.
     *
     * @param style the style option (can't be null)
     * @param value the value (can be null)
     */
    public void addStyle(Style style, String value)
    {
        if (style == null)
        {
            return;
        }
        addAttribute(Attribute.STYLE, style.name().toLowerCase().replaceAll("_", "-") + ": " + value + ";");
    }
    
    /**
     * Generates the tree of elements and returns it as a String.
     *
     * @return html code.
     */
    public String generate()
    {
        return Objects.requireNonNullElseGet(text, () -> MessageFormat.format("<{0} {1}>{2}</{0}>", tag.name().toLowerCase().replaceAll("_", "-"), generateAttributes(), generateContent()));
    }
    
    private String generateContent()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (HyperTextElement hyperTextElement : contentList)
        {
            stringBuilder.append(hyperTextElement.generate());
        }
        return new String(stringBuilder);
    }
    
    private String generateAttributes()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Attribute, String> attribute : attributeMap.entrySet())
        {
            String generatedAttribute = String.format(" %s=\"%s\"", attribute.getKey().name().toLowerCase().replaceAll("_", "-"), attribute.getValue());
            stringBuilder.append(generatedAttribute);
        }
        return new String(stringBuilder).trim();
    }
}
