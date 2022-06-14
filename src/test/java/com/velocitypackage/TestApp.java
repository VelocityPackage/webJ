package com.velocitypackage;

import com.velocitypackage.materials.application.AppRoot;
import com.velocitypackage.materials.components.Button;
import com.velocitypackage.materials.components.Form;
import com.velocitypackage.materials.components.Input;
import com.velocitypackage.materials.components.Page;
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
        
        Button button = new Button("Haha")
        {
            @Override
            public void onClick()
            {
                System.out.println("kaka");
            }
        };
        
        page.add(button);
        
        Input text = new Input("Text", Input.TYPE.TEXT);
        Input submit = new Input("Submit", Input.TYPE.SUBMIT);
        
        Form form = new Form()
        {
            @Override
            public void callback(Map<String, String> data)
            {
                System.out.println(data.get(getInputKey(text)));
            }
        };
        
        form.add(text);
        form.add(submit);
        
        page.add(form);
        
        AppRoot appRoot = new AppRoot();
        appRoot.setPage(page);
        return appRoot;
    }
}
