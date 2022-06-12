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
        
        Container header = new Container();
        header.putStyle(HyperTextElement.STYLE.WIDTH, "100%");
        
        Container left = new Container();
        Container middle = new Container();
        Container right = new Container();
        
        FlexGrid headerGrid = new FlexGrid();
        headerGrid.add(left);
        headerGrid.add(middle);
        headerGrid.add(right);
        header.add(headerGrid);
        
        Button home = new Button(Button.TYPE.SUCCESS, "Home")
        {
            @Override
            public void onClick()
            {
            }
        };
        
        left.add(home);
        
        Button apple = new Button(Button.TYPE.DANGER, "Apple Danger")
        {
            @Override
            public void onClick()
            {
            
            }
        };
        
        apple.putStyle(HyperTextElement.STYLE.FLOAT, "right");
        
        right.add(apple);
        
        page.add(header);
        
        header.putStyle(HyperTextElement.STYLE.BACKGROUND_COLOR, "#202020");
        
        AppRoot appRoot = new AppRoot();
        appRoot.setPage(page);
        return appRoot;
    }
}
