package com.velocitypackage.webJ.materials.hypertext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HyperTextBehaviorTest
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
    
    @BeforeEach
    void setUp()
    {
        hyperTextBehavior.setHyperTextElement(hyperTextElement);
    }
    
    @AfterEach
    void tearDown()
    {
        hyperTextBehavior.recompile();
    }
    
    @Test
    void setHyperTextElement()
    {
        hyperTextBehavior.setHyperTextElement(hyperTextElement);
        System.out.println(hyperTextBehavior.getTextRepresentation());
    }
    
    @Test
    void recompile()
    {
        hyperTextBehavior.recompile();
        System.out.println(hyperTextBehavior.getTextRepresentation());
    }
    
    @Test
    void onMessage()
    {
        hyperTextBehavior.onMessage(hyperTextBehavior.id, new HashMap<>());
    }
    
    @Test
    void getTextRepresentation()
    {
        System.out.println(hyperTextBehavior.getTextRepresentation());
    }
    
    @Test
    void addChild()
    {
        HyperTextBehavior hyperTextBehavior1 = new HyperTextBehavior()
        {
            @Override
            public void onInteract(Map<String, String> values)
            {
        
            }
        };
        hyperTextBehavior1.setHyperTextElement(new HyperTextElement(Tag.H1, null, null, null));
        hyperTextBehavior.add(hyperTextBehavior1);
        System.out.println(hyperTextBehavior.getTextRepresentation());
    }
}