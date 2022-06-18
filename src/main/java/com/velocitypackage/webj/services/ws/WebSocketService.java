package com.velocitypackage.webj.services.ws;

import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.tools.WebApplication;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public final class WebSocketService extends WebSocketServer
{
    private Map<WebSocket, HyperTextBehavior> clientList;
    private final WebApplication webApplication;
    
    public WebSocketService(int port, WebApplication webApplication)
    {
        super(new InetSocketAddress(port));
        this.webApplication = webApplication;
    }
    
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake)
    {
        clientList.put(conn, webApplication.build());
        conn.send(clientList.get(conn).build());
    }
    
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
        HyperTextBehavior appRoot = clientList.get(conn);
        if (appRoot != null)
        {
            clientList.remove(conn);
        }
    }
    
    @Override
    public void onMessage(WebSocket conn, String message)
    {
        HyperTextBehavior appRoot = clientList.get(conn);
        if (appRoot != null)
        {
            //id:<id> inputs:{}
            //id:<id> inputs:{<inputID>??<value>;;...}
            //TODO
            
            appRoot.onMessage("message", null);
            conn.send(appRoot.build());
        }
    }
    
    @Override
    public void onError(WebSocket conn, Exception ex)
    {
        HyperTextBehavior appRoot = clientList.get(conn);
        if (appRoot != null)
        {
            clientList.remove(conn);
        }
        System.out.println("Error with connection: " + conn.getResourceDescriptor());
    }
    
    @Override
    public void onStart()
    {
        clientList = new HashMap<>();
    }
}
