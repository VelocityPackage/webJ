package com.velocitypackage.webj.tools;

import com.velocitypackage.webj.materials.webJ.Application;
import com.velocitypackage.webj.services.file.FileService;
import com.velocitypackage.webj.services.http.HttpContext;
import com.velocitypackage.webj.services.http.HttpService;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

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
    private final Application application;
    
    private String frameHtmlResource, streamJsResource;
    
    /**
     * Creates a Server service management system for a specific application
     * @param httpPort http port
     * @param wsPort websocket port (not the same as http port)
     * @param application the application
     * @throws IOException throws the port is the same
     */
    public Manager(int httpPort, int wsPort, Application application) throws IOException
    {
        this.connections = new HashMap<>();
        this.httpPort = httpPort;
        this.wsPort = wsPort;
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
        webSocketServer.start();
        System.out.println("HTTP server listening on port " + httpPort);
        System.out.println("WebSocket server listening on port " + wsPort);
        System.out.println("http://localhost:" + httpPort);
        System.out.println("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + httpPort);
        System.out.println("http://" + InetAddress.getLocalHost().getHostName() + ":" + httpPort);
    }
    
    private void httpServiceSetup() throws IOException
    {
        httpService = new HttpService(httpPort);
        frameHtmlResource = FileService.getContentOfResource("frame.html").replaceFirst("%NAME%", application.getApplicationName());
        streamJsResource = FileService.getContentOfResource("stream.js").replaceFirst("%WSPORT%", "" + wsPort);
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
            public String content()
            {
                return streamJsResource;
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
