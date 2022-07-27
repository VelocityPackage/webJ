package com.velocitypackage.webJ.tools;

import com.velocitypackage.webJ.materials.webJ.Application;
import com.velocitypackage.webJ.materials.webJ.NotSupportedMessageFormat;
import com.velocitypackage.webJ.services.file.FileService;
import com.velocitypackage.webJ.services.http.HttpContext;
import com.velocitypackage.webJ.services.http.HttpFileContext;
import com.velocitypackage.webJ.services.http.HttpService;
import com.velocitypackage.webJ.services.ws.WebSocketService;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public final class WebJManager
{
    private HttpService httpService;
    private WebSocketService webSocketService;
    private final WebServer webServer;
    
    private final Map<WebSocketConnection, Application> connections;
    
    private final int port;
    private final Application application;
    
    private String frameHtmlResource, streamJsResource, robotsTxtResource;
    private byte[] faviconIco;
    
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
        this.httpServiceSetup();
        this.webSocketServiceSetup();
        this.webServer.add(httpService);
        this.webServer.add("/socket", webSocketService);
    }
    
    /**
     * starts all services
     */
    @SuppressWarnings("unused")
    public void start()
    {
        webServer.start();
        System.out.println("Webserver listening on port " + webServer.getPort());
        System.out.println("http://localhost:" + port);
        try
        {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("http://" + localhost.getHostAddress() + ":" + port);
            System.out.println("http://" + localhost.getHostName() + ":" + port);
        } catch (Exception ignore) {}
    }
    
    /**
     * stops all services
     */
    @SuppressWarnings("unused")
    public void stop()
    {
        webServer.stop();
        System.out.println("Server was successfully closed...");
    }
    
    /**
     * sets up the http service
     */
    private void httpServiceSetup() throws IOException
    {
        httpService = new HttpService();
        frameHtmlResource = FileService.getContentOfResource("frame.html")
                .replaceFirst("%NAME%", application.getApplicationName());
        if (application.getFavicon() != null)
        {
            frameHtmlResource = frameHtmlResource.replaceAll("%FAVICON%", application.getFavicon());
        } else
        {
            frameHtmlResource = frameHtmlResource.replaceAll("%FAVICON%", "");
        }
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
        streamJsResource = FileService.getContentOfResource("stream.js");
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
        robotsTxtResource = FileService.getContentOfResource("robots.txt");
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
        if (application.getFavicon() != null)
        {
            faviconIco = Base64.getDecoder().decode("data:image/x-icon;base64," + application.getFavicon());
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
                public byte[] content(String path)
                {
                    return faviconIco;
                }
            });
        }
    }
    
    /**
     * sets up the web socket service
     */
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
