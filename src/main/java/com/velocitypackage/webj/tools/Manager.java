package com.velocitypackage.webj.tools;

import com.velocitypackage.webj.materials.webJ.Application;
import com.velocitypackage.webj.services.file.FileService;
import com.velocitypackage.webj.services.http.HttpContext;
import com.velocitypackage.webj.services.http.HttpFileContext;
import com.velocitypackage.webj.services.http.HttpService;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public final class Manager
{
    private HttpService httpService;
    private WebSocketServer webSocketServer;
    private final Map<WebSocket, Application> connections;
    
    private final int httpPort, wsPort;
    private final File favicon;
    private final Application application;
    
    private String frameHtmlResource, streamJsResource, robotsTxtResource;
    
    /**
     * Creates a Server service management system for a specific application
     * @param httpPort http port
     * @param wsPort websocket port (not the same as http port)
     * @param favicon is the new page icon (only *.ico supported)
     * @param application the application
     * @throws IOException throws the port is the same
     */
    public Manager(int httpPort, int wsPort, File favicon, Application application) throws IOException
    {
        this.connections = new HashMap<>();
        this.httpPort = httpPort;
        this.wsPort = wsPort;
        this.favicon = favicon;
        if (!this.favicon.getName().endsWith(".ico")) {
            throw new IOException("Favicon not supported...");
        }
        this.application = application;
        this.httpServiceSetup();
        this.webSocketServiceSetup();
    }
    
    /**
     * starts all services
     * @throws UnknownHostException throws the port is the same
     */
    public void start() throws UnknownHostException
    {
        httpService.start();
        System.out.println("HTTP server listening on port " + httpPort);
        System.out.println("http://localhost:" + httpPort);
        System.out.println("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + httpPort);
        System.out.println("http://" + InetAddress.getLocalHost().getHostName() + ":" + httpPort);
        webSocketServer.start();
        System.out.println("WebSocket server listening on port " + wsPort);
        System.out.println("ws://localhost:" + wsPort);
        System.out.println("ws://" + InetAddress.getLocalHost().getHostAddress() + ":" + wsPort);
        System.out.println("ws://" + InetAddress.getLocalHost().getHostName() + ":" + wsPort);
    }
    
    private void httpServiceSetup() throws IOException
    {
        httpService = new HttpService(httpPort);
        frameHtmlResource = FileService.getContentOfResource("frame.html").replaceFirst("%NAME%", application.getApplicationName());
        streamJsResource = FileService.getContentOfResource("stream.js").replaceFirst("%WSPORT%", "" + wsPort);
        robotsTxtResource = FileService.getContentOfResource("robots.txt");
        httpService.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                return path.equals("/");
            }
    
            @Override
            public String contentType()
            {
                return "text/html";
            }
    
            @Override
            public String content()
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
            public String content()
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
            public String content()
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
            public File content()
            {
                return favicon;
            }
        });
    }
    
    private void webSocketServiceSetup()
    {
        webSocketServer = new WebSocketServer(new InetSocketAddress(wsPort))
        {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake)
            {
                try
                {
                    connections.put(conn, application.clone());
                } catch (CloneNotSupportedException e)
                {
                    e.printStackTrace();
                }
                conn.send(connections.get(conn).getTextRepresentation());
            }
    
            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote)
            {
                connections.remove(conn);
            }
    
            @Override
            public void onMessage(WebSocket conn, String message)
            {
                connections.get(conn).onMessage(message);
                conn.send(connections.get(conn).getTextRepresentation());
            }
    
            @Override
            public void onError(WebSocket conn, Exception ex)
            {
            }
    
            @Override
            public void onStart()
            {
            }
        };
        webSocketServer.setReuseAddr(true);
    }
}
