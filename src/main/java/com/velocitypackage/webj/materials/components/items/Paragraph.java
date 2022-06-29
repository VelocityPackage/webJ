package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class Paragraph extends Item
{
    public Paragraph(String p)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, null, null, styles(), p));
    }
    
    public Paragraph(String p, Bootstrap... bootstraps)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, bootstraps, null, null, p));
    }
    
    public Paragraph(String p, Style... styles)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, null, null, styles, p));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
