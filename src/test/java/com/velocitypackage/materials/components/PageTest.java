package com.velocitypackage.materials.components;

import org.junit.jupiter.api.Test;

class PageTest
{
    
    @Test
    void getHyperText()
    {
        Page page = new Page();
        
        Container inner = new Container();
        
        inner.add(new Button("Home", "home_button")
        {
            @Override
            public void onClick()
            {
            
            }
            
            @Override
            public void onClick(String id)
            {
            
            }
        });
        
        inner.add(new InlineFrame("https://amazon.de"));
        inner.add(new TextArea("If the iFrame did not work use the link"));
        inner.add(new Link("https://amazon.de", "Amazon"));
        
        page.add(inner);
        
        page.add(new TextArea("The end"));
        
        System.out.println(page.getHyperText());
        
    }
}