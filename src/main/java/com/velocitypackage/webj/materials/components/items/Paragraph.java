package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class Paragraph extends Item
{
    /**
     * Creates a new paragraph
     * @param text the paragraph text content
     */
    public Paragraph(String text)
    {
        setHyperTextElement(new HyperTextElement(Tag.P, null, null, zeroMargin(), text));
    }
    
    /**
     * Creates a new paragraph
     * @param text the paragraph text content
     * @param bootstraps bootstrap options
     */
    public Paragraph(String text, Bootstrap... bootstraps)
    {
        setHyperTextElement(new HyperTextElement(Tag.P, bootstraps, null, zeroMargin(), text));
    }
    
    /**
     * Creates a new paragraph
     * @param text the paragraph text content
     * @param styles style options
     */
    public Paragraph(String text, Style... styles)
    {
        setHyperTextElement(new HyperTextElement(Tag.P, null, null, combine(styles, zeroMargin()), text));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
