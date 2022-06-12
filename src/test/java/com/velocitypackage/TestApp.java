package com.velocitypackage;

import com.velocitypackage.materials.components.Button;
import com.velocitypackage.materials.components.Page;
import com.velocitypackage.tools.WebGraphicsApplication;

public class TestApp extends WebGraphicsApplication
{
    
    
    public TestApp(){
    
        Page page = new Page();
        setPage(page);
        Button button = new Button("Button")
        {
            @Override
            public void onClick()
            {
            
            }
        };
        Button button2 = new Button("Button")
        {
            @Override
            public void onClick()
            {
            
            }
        };
        System.out.println(button.id);
        System.out.println(button2.id);
        
    }
    
}
