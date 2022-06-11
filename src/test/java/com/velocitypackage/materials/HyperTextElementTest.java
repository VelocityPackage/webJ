package com.velocitypackage.materials;

import com.velocitypackage.materials.components.HyperTextElement;
import org.junit.jupiter.api.Test;

class HyperTextElementTest
{
    
    @Test
    void compile()
    {
        HyperTextElement header = new HyperTextElement(HyperTextElement.TAG.H1, "Hallo Welt");
        header.addStyle(HyperTextElement.STYLE.MARGIN, 8);
        header.addStyle(HyperTextElement.STYLE.MARGIN, 3);
        
        header.addClass("header");
        header.addId("head");
        
        System.out.println(header);
    }
}