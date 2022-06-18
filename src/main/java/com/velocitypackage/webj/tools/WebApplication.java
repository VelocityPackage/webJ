package com.velocitypackage.webj.tools;

import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.services.file.FileService;
import com.velocitypackage.webj.services.http.HttpContext;
import com.velocitypackage.webj.services.http.HttpService;
import com.velocitypackage.webj.services.ws.WebSocketService;

import java.io.IOException;
import java.net.InetAddress;

public abstract class WebApplication
{
    public abstract HyperTextBehavior build();
    
    public final void start(int wsPort, int httpPort, String websiteTitle) throws IOException
    {
        WebSocketService webSocketService = new WebSocketService(wsPort, this);
        webSocketService.setReuseAddr(true);
        webSocketService.start();
        System.out.println("WebSocket server listening on port " + wsPort);
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
        System.out.println("HTTP server listening on port " + httpPort);
        System.out.println("http://localhost:" + httpPort);
        System.out.println("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + httpPort);
        System.out.println("http://" + InetAddress.getLocalHost().getHostName() + ":" + httpPort);
    }
}
