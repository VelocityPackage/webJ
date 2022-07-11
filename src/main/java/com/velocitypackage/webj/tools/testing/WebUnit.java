package com.velocitypackage.webj.tools.testing;

import com.velocitypackage.webj.materials.hypertext.HyperTextPage;
import com.velocitypackage.webj.materials.webJ.Application;
import com.velocitypackage.webj.services.http.HttpContext;

import java.io.IOException;
import java.util.HashMap;

public class WebUnit
{
    private final HttpUnitServer httpUnitServer;
    
    public WebUnit(int port, Application application) throws IOException
    {
        httpUnitServer = new HttpUnitServer(port);
        for (HyperTextPage hyperTextPage : application.getPages())
        {
            hyperTextPage.onMessage("", new HashMap<>());
            httpUnitServer.add(new HttpContext()
            {
                @Override
                public boolean acceptPath(String path)
                {
                    return path.trim().equals(hyperTextPage.getPath().trim());
                }
    
                @Override
                public String contentType()
                {
                    return "text/html";
                }
    
                @Override
                public String content(String path)
                {
                    return String.format("""
                            <html>
                                <head>
                                    <meta charset="utf-8">
                                    <meta name="viewport" content="width=device-width, initial-scale=1">
                                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
                                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
                                    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />
                                </head>
                                <body>
                                    %s
                                </body>
                            </html>
                            """, hyperTextPage.getTextRepresentation());
                }
            });
        }
    }
    
    public void start()
    {
        httpUnitServer.start();
        System.out.println("Testing Server was started");
    }
}
