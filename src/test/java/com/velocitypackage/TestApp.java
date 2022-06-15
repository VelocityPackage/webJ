package com.velocitypackage;

import com.velocitypackage.materials.application.AppRoot;
import com.velocitypackage.materials.components.*;
import com.velocitypackage.tools.WebApplication;

import java.io.IOException;
import java.util.Map;

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
        
        
        Row row = new Row();
        page.add(row);
        row.add(new Sidebar(Theme.DARK, 280));
        View view = new View();
        view.putStyle(HyperTextElement.STYLE.BACKGROUND_COLOR, "black");
        row.add(view);
        
        Input name = new Input("Name", Input.TYPE.TEXT);
        Input submit = new Input("Submit", Input.TYPE.SUBMIT);
        
        Form form = new Form()
        {
            @Override
            public void callback(Map<String, String> data)
            {
                System.out.println("New register: " + data.get(getInputKey(name)));
            }
        };
        
        form.add(name, submit);
        
        view.add(form);
        
        AppRoot appRoot = new AppRoot();
        appRoot.setPage(page);
        return appRoot;
    }
}
