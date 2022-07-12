package com.velocitypackage.webj.tools;

import com.velocitypackage.webj.materials.webJ.Application;
import com.velocitypackage.webj.materials.webJ.NotSupportedMessageFormat;
import com.velocitypackage.webj.services.file.FileService;
import com.velocitypackage.webj.services.http.HttpContext;
import com.velocitypackage.webj.services.http.HttpFileContext;
import com.velocitypackage.webj.services.http.HttpService;
import com.velocitypackage.webj.services.ws.WebSocketService;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class WebJManager
{
    private HttpService httpService;
    private WebSocketService webSocketService;
    private final WebServer webServer;
    
    private final Map<WebSocketConnection, Application> connections;
    
    private final int port;
    private final Application application;
    
    private String frameHtmlResource, streamJsResource, robotsTxtResource;
    
    private final InetAddress localhost;
    
    /**
     * Creates a Server service management system for a specific application
     * @param port port of webpage
     * @param application the application
     * @throws IOException throws the port is the same
     */
    public WebJManager(int port, Application application) throws IOException
    {
        this.connections = new HashMap<>();
        this.port = port;
        this.application = application;
        this.webServer = WebServers.createWebServer(this.port);
        this.localhost = InetAddress.getLocalHost();
        this.httpServiceSetup();
        this.webSocketServiceSetup();
        this.webServer.add(httpService);
        this.webServer.add("/socket", webSocketService);
    }
    
    /**
     * starts all services
     */
    public void start()
    {
        webServer.start();
        System.out.println("Webserver listening on port " + webServer.getPort());
        System.out.println("http://localhost:" + port);
        try
        {
            System.out.println("http://" + localhost.getHostAddress() + ":" + port);
            System.out.println("http://" + localhost.getHostName() + ":" + port);
        } catch (Exception ignore) {}
    }
    
    /**
     * stops all services
     */
    public void stop()
    {
        webServer.stop();
        System.out.println("Server was successfully closed...");
    }
    
    private void httpServiceSetup() throws IOException
    {
        httpService = new HttpService();
        frameHtmlResource = FileService.getContentOfResource("frame.html").replaceFirst("%NAME%", application.getApplicationName());
        streamJsResource = FileService.getContentOfResource("stream.js").replaceFirst("%WSPORT%", "" + port);
        robotsTxtResource = FileService.getContentOfResource("robots.txt");
        httpService.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                for (String notAllowed : new String[]{"/stream", "/robots.txt", "/favicon.ico", "/socket"})
                {
                    if (Objects.equals(path, notAllowed))
                    {
                        return false;
                    }
                }
                return true;
            }
    
            @Override
            public String contentType()
            {
                return "text/html";
            }
    
            @Override
            public String content(String path)
            {
                return frameHtmlResource;
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
            public String contentType()
            {
                return "text/javascript";
            }
    
            @Override
            public String content(String path)
            {
                return streamJsResource;
            }
        });
        httpService.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                return path.equals("/robots.txt");
            }
    
            @Override
            public String contentType()
            {
                return "text/plain";
            }
    
            @Override
            public String content(String path)
            {
                return robotsTxtResource;
            }
        });
        httpService.add(new HttpFileContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                return path.equals("/favicon.ico");
            }
    
            @Override
            public String contentType()
            {
                return "image/x-icon";
            }
    
            @Override
            public File content(String path)
            {
                return application.getFavicon();
            }
        });
    }
    
    private void webSocketServiceSetup()
    {
        webSocketService = new WebSocketService()
        {
            @Override
            public void onOpen(WebSocketConnection connection)
            {
                try
                {
                    connections.put(connection, application.clone());
                } catch (CloneNotSupportedException e)
                {
                    e.printStackTrace();
                }
                connections.get(connection).setForceUpdate(() -> connection.send(connections.get(connection).getTextRepresentation())); //force update
                //connection.send(connections.get(connection).getTextRepresentation());
            }
    
            @Override
            public void onClose(WebSocketConnection connection)
            {
                connections.remove(connection);
            }
    
            @Override
            public void onMessage(WebSocketConnection connection, String message)
            {
                try
                {
                    connections.get(connection).onMessage(message);
                } catch (NotSupportedMessageFormat e)
                {
                    e.printStackTrace();
                }
                connection.send(connections.get(connection).getTextRepresentation());
            }
        };
    }
}
