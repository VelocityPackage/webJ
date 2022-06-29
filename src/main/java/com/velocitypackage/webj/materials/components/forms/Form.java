package com.velocitypackage.webj.materials.components.forms;

import com.velocitypackage.webj.materials.components.Align;
import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.*;

import java.util.Map;

public class Form extends Component
{
    
    public Form(Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, null, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.ACTION, "javascript:void(0);")}, null));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Form(Align contentAlign, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, null, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.ACTION, "javascript:void(0);")}, new Style[]{new Style(Style.StyleIdentifier.TEXT_ALIGN, contentAlign.name().toLowerCase())}));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Form(Bootstrap[] bootstraps, Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, bootstraps, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.ACTION, "javascript:void(0);")}, null));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    public Form(Bootstrap[] bootstraps, Align contentAlign, Component... children)
    {
        Style[] basic = new Style[]{new Style(Style.StyleIdentifier.TEXT_ALIGN, contentAlign.name().toLowerCase())};
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, bootstraps, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.ACTION, "javascript:void(0);")}, basic));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(child);
        }
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    
    }
}
