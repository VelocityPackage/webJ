package com.velocitypackage.webJ.materials.hypertext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HyperTextPageTest
{
    HyperTextElement hyperTextElement = new HyperTextElement(Tag.H1, new Bootstrap[]{
            Bootstrap.H1
    }, new Attribute[]{
            new Attribute(Attribute.AttributeIdentifier.ID, "heading")
    }, new Style[]{
            new Style(Style.StyleIdentifier.COLOR, "black")
    });
    
    HyperTextBehavior hyperTextBehavior = new HyperTextBehavior()
    {
        @Override
        public void onInteract(Map<String, String> values)
        {
            System.out.println("onInteract(); -> was calling");
        }
    };
    
    HyperTextPage hyperTextPage;
    
    @BeforeEach
    void setUp()
    {
        hyperTextBehavior.setHyperTextElement(hyperTextElement);
        hyperTextPage = new HyperTextPage("Page", "/", new Style[]{
                new Style(Style.StyleIdentifier.BACKGROUND_COLOR, "black")
        }, new Bootstrap[]{
                Bootstrap.BORDER_DARK
        }, hyperTextBehavior);
    }
    
    @AfterEach
    void tearDown()
    {
        hyperTextBehavior.recompile();
    }
    
    @Test
    void getPath()
    {
        assertEquals("/", hyperTextPage.getPath());
    }
    
    @Test
    void getName()
    {
        assertEquals("Page", hyperTextPage.getName());
    }
    
    @Test
    void getStyles()
    {
        assertEquals(new Style[]{new Style(Style.StyleIdentifier.BACKGROUND_COLOR, "black")}, hyperTextPage.getStyles());
    }
    
    @Test
    void getBootstraps()
    {
        assertEquals(new Bootstrap[]{Bootstrap.BORDER_DARK}, hyperTextPage.getBootstraps());
    }
    
    @Test
    void getTextRepresentation()
    {
        assertNotEquals("", hyperTextPage.getTextRepresentation());
    }
    
    @Test
    void onMessage()
    {
        hyperTextPage.onMessage(hyperTextBehavior.id, new HashMap<>());
    }
}