package com.velocitypackage.database;

import com.velocitypackage.webj.materials.components.Panel;
import com.velocitypackage.webj.materials.components.Row;
import com.velocitypackage.webj.materials.components.items.Image;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;

import java.io.File;
import java.io.IOException;

public class ItemTester extends WebJApplication
{
    public static void main(String[] args) throws IOException
    {
        WebJApplication webJApplication = new ItemTester();
        webJApplication.start(8081, 8080, "ItemTester");
    }
    
    @Override
    public HyperTextBehavior build()
    {
        return new Row(
                new Panel(),
                new Panel(
                        new Image(new File("img.png"))
                ),
                new Panel()
        );
    }
}
