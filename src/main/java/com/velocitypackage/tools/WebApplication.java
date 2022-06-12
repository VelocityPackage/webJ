package com.velocitypackage.tools;

import com.velocitypackage.materials.application.AppRoot;
import com.velocitypackage.services.files.FileService;
import com.velocitypackage.services.http.HttpContext;
import com.velocitypackage.services.http.HttpService;
import com.velocitypackage.services.ws.WebSocketService;

import java.io.IOException;

public abstract class WebApplication
{
    public abstract AppRoot build();
    
    public void start(int wsPort, int httpPort, String websiteTitle) throws IOException
    {
        WebSocketService webSocketService = new WebSocketService(wsPort, this);
        webSocketService.start();
        HttpService httpService = new HttpService(httpPort);
        httpService.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                return path.equals("/");
            }
            @Override
            public String content()
            {
                try
                {
                    return FileService.getContentOfResource("frame.html").replaceFirst("%NAME%", websiteTitle);
                } catch (IOException e)
                {
                    return "";
                }
            }
        });
        httpService.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                return path.equals("/stream");
            }
    
            @Override
            public String content()
            {
                try
                {
                    return FileService.getContentOfResource("stream.js").replaceFirst("%WSPORT%", "" + wsPort);
                } catch (IOException e)
                {
                    return "";
                }
            }
        });
        httpService.start();
    }
}
