package com.velocitypackage.webj.materials.hypertext;

import java.util.ArrayList;
import java.util.List;

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
}
