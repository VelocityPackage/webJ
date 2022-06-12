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
        Image image = new Image(new File("image.webp"));
        image.putStyle(HyperTextElement.STYLE.WIDTH, "100%");
        page.add(image);
        
        AppRoot appRoot = new AppRoot();
        appRoot.setPage(page);
        return appRoot;
    }
}
