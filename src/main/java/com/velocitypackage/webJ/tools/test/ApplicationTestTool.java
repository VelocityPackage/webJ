package com.velocitypackage.webJ.tools.test;

import com.velocitypackage.webJ.materials.hypertext.Bootstrap;
import com.velocitypackage.webJ.materials.hypertext.HyperTextPage;
import com.velocitypackage.webJ.materials.hypertext.Style;
import com.velocitypackage.webJ.materials.webJ.Application;
import com.velocitypackage.webJ.services.http.HttpContext;

import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings("unused")
public class ApplicationTestTool
{
    private final HttpServer httpServer;
    
    /**
     *
     * @param port the test Port.
     * @param application The application to test.
     * @throws IOException If an error occurs.
     */
    public ApplicationTestTool(int port, Application application) throws IOException
    {
        httpServer = new HttpServer(port);
        for (HyperTextPage hyperTextPage : application.getPages())
        {
            hyperTextPage.onMessage("", new HashMap<>());
            httpServer.add(new HttpContext()
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
                                <body style="%s" class="%s">
                                    %s
                                </body>
                            </html>
                            """, styleInterpreter(hyperTextPage.getStyles()), bootstrapInterpreter(hyperTextPage.getBootstraps()), hyperTextPage.getTextRepresentation());
                }
            });
        }
    }
    
    private static String styleInterpreter(Style[] styles)
    {
        StringBuilder styleInterpretation = new StringBuilder();
        for (Style style : styles)
        {
            styleInterpretation.append(style.getIdentifier().name().toLowerCase().replaceAll("_", "-")).append(":").append(style.getValue()).append(";");
        }
        return new String(styleInterpretation);
    }
    
    private static String bootstrapInterpreter(Bootstrap[] bootstraps)
    {
        StringBuilder bootstrapInterpretation = new StringBuilder();
        for (Bootstrap bootstrap : bootstraps)
        {
            bootstrapInterpretation.append(bootstrap.name().toLowerCase().replaceAll("_", "-")).append(" ");
        }
        return new String(bootstrapInterpretation);
    }
    
    /**
     * Starts the ApplicationTestTool
     */
    public void start()
    {
        httpServer.start();
        System.out.println("Testing Server was started");
    }
}
