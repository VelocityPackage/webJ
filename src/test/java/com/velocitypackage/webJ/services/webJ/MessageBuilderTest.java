package com.velocitypackage.webJ.services.webJ;

import com.velocitypackage.webJ.materials.hypertext.Bootstrap;
import com.velocitypackage.webJ.materials.hypertext.Style;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBuilderTest
{
    MessageBuilder builder;
    
    @BeforeEach
    void setUp()
    {
        builder = new MessageBuilder("TestTitle", "<tag></tag>", new Style[]{
                new Style(Style.StyleIdentifier.BACKGROUND_COLOR, "black")
        }, new Bootstrap[]{
                Bootstrap.BORDER_DARK
        });
    }
    
    @AfterEach
    void tearDown()
    {
        builder = null;
    }
    
    @Test
    void getTitle()
    {
        assertEquals("TestTitle", builder.getTitle());
    }
    
    @Test
    void getStyleInert()
    {
        assertEquals("background-color:black;", builder.getStyleInert());
    }
    
    @Test
    void getBootstrapInert()
    {
        assertEquals("border-dark", builder.getBootstrapInert());
    }
    
    @Test
    void testToString()
    {
        assertEquals("name:TestTitle&&style:background-color:black;&&classes:border-dark&&hypertext:<tag></tag>", builder.toString());
    }
}