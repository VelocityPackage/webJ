package com.velocitypackage.webJ.tools;

import com.velocitypackage.webJ.materials.hypertext.*;
import com.velocitypackage.webJ.materials.webJ.Application;

import java.io.File;
import java.util.Map;

public class TestApplication extends Application
{
    public TestApplication()
    {
        setApplicationName("Application");
        setFavicon(new File("src/test/resources/favicon.ico"));
        addPage(homePage());
        addPage(secondPage());
    }
    
    HyperTextPage homePage()
    {
        HyperTextBehavior button = new HyperTextBehavior()
        {
            @Override
            public void onInteract(Map<String, String> values)
            {
        
            }
        };
        button.setHyperTextElement(new HyperTextElement(Tag.A, null, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, "/second")}, null, "To Second Page"));
        return new HyperTextPage("Home", "/",
                button
                );
    }
    
    HyperTextPage secondPage()
    {
        HyperTextBehavior button = new HyperTextBehavior()
        {
            @Override
            public void onInteract(Map<String, String> values)
            {
            
            }
        };
        button.setHyperTextElement(new HyperTextElement(Tag.A, null, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, "/")}, null, "To Home Page"));
        return new HyperTextPage("Second", "/second",
                button
        );
    }
}
