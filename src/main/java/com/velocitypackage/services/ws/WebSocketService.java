package com.velocitypackage.services.ws;

import com.velocitypackage.materials.application.Website;

import com.velocitypackage.tools.WebApplication;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public final class  WebSocketService extends WebSocketServer
{
    private Map<WebSocket, Website> clientList;
    private final WebApplication webApplication;
    
    public WebSocketService(int port, WebApplication webApplication){
        super(new InetSocketAddress(port));
        this.webApplication = webApplication;
    }
    
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake)
    {
        Website website = webApplication.build();
        clientList.put(conn, webApplication.build());
        conn.send(website.getHyperText());
    }
    
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
    
    }
    
    @Override
    public void onMessage(WebSocket conn, String message)
    {
    
    }
    
    @Override
    public void onError(WebSocket conn, Exception ex)
    {
    
    }
    
    @Override
    public void onStart()
    {
        clientList = new HashMap<>();
    }
}
