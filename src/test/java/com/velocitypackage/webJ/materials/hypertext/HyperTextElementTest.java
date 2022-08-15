package com.velocitypackage.webJ.materials.hypertext;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HyperTextElementTest
{
    HyperTextElement hyperTextElement = new HyperTextElement(Tag.H1, new Bootstrap[]{
        Bootstrap.H1
    }, new Attribute[]{
        new Attribute(Attribute.AttributeIdentifier.ID, "heading")
    }, new Style[]{
        new Style(Style.StyleIdentifier.COLOR, "black")
    });
    
    @Test
    void getTag()
    {
        assertEquals("h1", hyperTextElement.getTag());
    }
    
    @Test
    void getAttributes()
    {
        assertNotEquals("", hyperTextElement.getAttributes());
        System.out.println(hyperTextElement.getAttributes());
    }
    
    @Test
    void getId()
    {
        assertNotEquals("", hyperTextElement.getId());
        System.out.println(hyperTextElement.getId());
    }
    
    @Test
    void getText()
    {
        System.out.println(hyperTextElement.getText());
    }
}