package com.velocitypackage.materials.components;

import org.junit.jupiter.api.Test;

class PageTest
{
    
    @Test
    void getHyperText()
    {
        Page page = new Page();
        
        View view = new View();
        
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
        
        inner.add(new Button(Button.TYPE.DANGER, "Home", "also")
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
        inner.add(new Text("If the iFrame did not work use the link"));
        inner.add(new Link("https://amazon.de", "Amazon"));
        
        view.add(inner);
        
        view.add(new Text("The end"));
        
        page.add(view);
        
        System.out.println(page.getHyperText());
        
    }
}