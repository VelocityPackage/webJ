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
        
        inner.add(new Link("https://amazon.de", "Amazon"));
        
        view.add(inner);
        
        view.add(new Text("The end"));
        
        List listL = new List(List.Theme.LIGHT);
        List listD = new List(List.Theme.DARK);
        
        listL.add(new List.Item("Home", "home", true)
        {
            @Override
            void onClick()
            {
            
            }
        });
        
        listL.add(new List.Item("another", "home", false)
        {
            @Override
            void onClick()
            {
            
            }
        });
        
        listD.add(new List.Item("Home", "home", true)
        {
            @Override
            void onClick()
            {
            
            }
        });
        
        listD.add(new List.Item("another", "home", false)
        {
            @Override
            void onClick()
            {
            
            }
        });
        
        Header head = new Header();
        
        head.add(new Header.Item("Home", "test", true)
        {
            @Override
            public void onClick()
            {
            
            }
        });
        
        head.add(new Header.Item("WebGraphics", "test", false)
        {
            @Override
            public void onClick()
            {
            
            }
        });
        
        head.add(new Header.Item("Velfa", "test", false)
        {
            @Override
            public void onClick()
            {
            
            }
        });
        
        head.add(new Header.Item("jNode", "test", false)
        {
            @Override
            public void onClick()
            {
            
            }
        });
        
        head.add(new Header.Item("Jarcob", "test", false)
        {
            @Override
            public void onClick()
            {
            
            }
        });
        
        page.add(head);
        
        page.add(view);
        
        FlexGrid flexGrid = new FlexGrid();
        
        flexGrid.add(listL);
        flexGrid.add(listD);
        
        flexGrid.add(listL);
        flexGrid.add(listD);
        
        flexGrid.add(listL);
        flexGrid.add(listD);
        
        flexGrid.add(listL);
        flexGrid.add(listD);
        
        
        page.add(flexGrid);
        
        System.out.println(page.getHyperText());
        
    }
}