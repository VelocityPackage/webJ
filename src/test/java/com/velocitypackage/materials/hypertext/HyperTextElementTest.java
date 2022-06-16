package com.velocitypackage.materials.hypertext;

import org.junit.jupiter.api.Test;

class HyperTextElementTest
{
    
    @Test
    void generate()
    {
        HyperTextElement hyperTextElement = new HyperTextElement(Tag.LINK);
        hyperTextElement.addAttribute(Attribute.HREF, "https://amazon.com");
        hyperTextElement.addClass(Bootstrap.LINK_DANGER);
        //hyperTextElement.addContent(); content
        hyperTextElement.addId("lalala");
        hyperTextElement.addId("gnoe");
        hyperTextElement.addStyle(Style.MARGIN, "10px");
        hyperTextElement.addContent(new HyperTextElement("Bla"));
        
        System.out.println(hyperTextElement.generate());
    }
}