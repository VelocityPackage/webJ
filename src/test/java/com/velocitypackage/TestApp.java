package com.velocitypackage;

import com.velocitypackage.materials.application.AppRoot;
import com.velocitypackage.materials.components.*;
import com.velocitypackage.tools.WebApplication;

import java.io.File;
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
        
        Sidebar bar = new Sidebar(Theme.DARK, 280);
        
        
        List list = new List(Theme.DARK);
        list.add(new List.Item("Home", true)
        {
            @Override
            public void onClick()
            {
            
            }
        });
        list.add(new List.Item("Projects", false)
        {
            @Override
            public void onClick()
            {
            
            }
        });
        list.add(new List.Item("Contact", false)
        {
            @Override
            public void onClick()
            {
            
            }
        });
        
        Button button = new Button("Delete All", Button.TYPE.DANGER)
        {
            @Override
            public void onClick()
            {
                System.out.println("delete");
            }
        };
        
        button.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        button.putStyle(HyperTextElement.STYLE.MARGIN_BOTTOM, "10px");
        button.putStyle(HyperTextElement.STYLE.MARGIN_TOP, "10px");
        
        bar.add(list);
        bar.add(button);
        
        Image image = new Image(new File("8k.jpeg"));
        image.putStyle(HyperTextElement.STYLE.HEIGHT, "100vh");
        image.putStyle(HyperTextElement.STYLE.WIDTH, "100vw");
        
        
        Box box = new Box(Box.TILT.HORIZONTAL);
        box.putStyle(HyperTextElement.STYLE.WIDTH, "100vw");
        box.add(bar);
        box.add(image);
        
        page.add(box);
        
        
        AppRoot appRoot = new AppRoot();
        appRoot.setPage(page);
        return appRoot;
    }
}
