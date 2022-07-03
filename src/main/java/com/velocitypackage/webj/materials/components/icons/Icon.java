package com.velocitypackage.webj.materials.components.icons;

import com.velocitypackage.webj.materials.components.Color;
import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.Attribute;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

/**
 * Click to see all Icons and how they look ->
 * <a href="https://fonts.google.com/icons">Icons</a>
 */
public class Icon extends Component
{
    public Icon(IconKey key) {
        super.setHyperTextElement(new HyperTextElement(Tag.SPAN, null, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.CLASS, "material-symbols-outlined")}, null, key.name().toLowerCase()));
    }
    
    public Icon(IconKey key, Color color) {
        super.setHyperTextElement(new HyperTextElement(Tag.SPAN, null, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.CLASS, "material-symbols-outlined")}, new Style[]{new Style(Style.StyleIdentifier.COLOR, color.getHex())}, key.name().toLowerCase()));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
