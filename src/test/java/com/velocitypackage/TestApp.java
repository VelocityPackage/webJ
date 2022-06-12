package com.velocitypackage;

import com.velocitypackage.materials.application.AppRoot;
import com.velocitypackage.materials.components.*;
import com.velocitypackage.tools.WebApplication;

import java.io.IOException;

public class TestApp extends WebApplication
{
    public static void main(String[] args) throws IOException
    {
        TestApp testApp = new TestApp();
        testApp.start(8081, 8080, "TestSite");
    }
    
    @Override
    public AppRoot build()
    {
        Page page = new Page();
        
        Header header = new Header();
        page.add(header);
        Header.Item button = new Header.Item("Projects", false)
        {
            @Override
            public void onClick()
            {
                System.out.println("projects");
            }
        };
        Header.Item button2 = new Header.Item("Home", true)
        {
            @Override
            public void onClick()
            {
                System.out.println("home");
            }
        };
        header.add(button2);
        header.add(button);
        
        
        View view = new View();
        page.add(view);
        
        FlexGrid flexGrid = new FlexGrid();
        
        view.add(flexGrid);
        
        Container container = new Container();
        container.putStyle(HyperTextElement.STYLE.HEIGHT, "50px");
        container.putStyle(HyperTextElement.STYLE.BACKGROUND_COLOR, "black");
        
        
        flexGrid.add(container);
        flexGrid.add(container);
        flexGrid.add(container);
        flexGrid.add(container);
        flexGrid.add(container);
        flexGrid.add(container);
        
        
        Container footer = new Container();
        page.add(footer);
        footer.putStyle(HyperTextElement.STYLE.WIDTH, "100%");
        Text text = new Text("© 2021 Marvin Mielchen. All Rights Reserved.");
        footer.add(text);
        Link link = new Link("https://www.youtube.com/", "Youtube");
        footer.add(link);
        
        AppRoot appRoot = new AppRoot();
        appRoot.setPage(page);
        return appRoot;
    }
}
