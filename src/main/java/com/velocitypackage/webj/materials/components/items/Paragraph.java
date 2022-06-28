package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class Paragraph extends Item
{
    public Paragraph(String p)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, null, null, null, p));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
