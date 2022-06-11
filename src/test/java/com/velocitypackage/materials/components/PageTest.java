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
        
        Header head = new Header();
        
        head.add(new Header.Item("Home", "test")
        {
            @Override
            void onClick()
            {
            
            }
        });
        
        head.add(new Header.Item("WebGraphics", "test")
        {
            @Override
            void onClick()
            {
            
            }
        });
        
        head.add(new Header.Item("Velfa", "test")
        {
            @Override
            void onClick()
            {
            
            }
        });
        
        head.add(new Header.Item("jNode", "test")
        {
            @Override
            void onClick()
            {
            
            }
        });
        
        head.add(new Header.Item("Jarcob", "test")
        {
            @Override
            void onClick()
            {
            
            }
        });
        
        page.add(head);
        
        page.add(view);
        
        FlexGrid flexGrid = new FlexGrid();
        
        flexGrid.add(new Text("blablablabla"));
        flexGrid.add(new Text("d"));
        flexGrid.add(new Text("alksdfkasdbfäabjsädfkadsjföasködjföklasdflöjasdlflöasdflkalsökdf"));
        flexGrid.add(new Text("blablablabla"));
        flexGrid.add(new Text("d"));
        flexGrid.add(new Text("blablablabla"));
        flexGrid.add(new Text("asdasdfasdfadsfasdf"));
        flexGrid.add(new Text("adasdfasdfasdfadsfasdfasf"));
        
        Grid grid = new Grid(4);
        grid.add(new Text("blablabla"));
        grid.add(new Text(""));
        grid.add(new Text("blablabla"));
        grid.add(new Text("alksdfkasdbfäabjsädfkadsjföasködjföklasdflöjasdlflöasdflkalsökdf"));
        grid.add(new Text("dddd"));
        grid.add(new Text("s"));
        grid.add(new Text("asdfasdfasdfasdfasdfasf"));
        grid.add(new Text("blablabla"));
        
        page.add(flexGrid);
        page.add(grid);
        
        System.out.println(page.getHyperText());
        
    }
}