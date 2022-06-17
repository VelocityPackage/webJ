package com.velocitypackage.webj.materials.hypertext;

/**
 *
 */
public class TextElement implements Element
{
    private final String text;
    
    /**
     * @param text
     */
    public TextElement(String text)
    {
        this.text = text;
    }
    
    /**
     * @return
     */
    public String getText()
    {
        return text;
    }
}
