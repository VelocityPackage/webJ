package com.velocitypackage.webj.services.ws;

import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.tools.WebJApplication;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public final class WebSocketService extends WebSocketServer
{
    private Map<WebSocket, HyperTextBehavior> clientList;
    private final WebJApplication webApplication;
    
    public WebSocketService(int port, WebJApplication webApplication)
    {
        super(new InetSocketAddress(port));
        this.webApplication = webApplication;
    }
    
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake)
    {
        clientList.put(conn, webApplication.build());
        conn.send(clientList.get(conn).getTextRepresentation());
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
            String id = message.split(" ")[0].replace("id:", "").trim();
            String[] inputs = message.split(" ")[1].replace("inputs:\\{", "").replace("\\}", "").split(";;");
            Map<String, String> inputMap = new HashMap<>();
            if (message.contains("??"))
            {
                for (String s : inputs)
                {
                    inputMap.put(s.split("\\?\\?")[0], s.split("\\?\\?")[1]);
                }
            }
            appRoot.onMessage(id, inputMap);
            conn.send(appRoot.getTextRepresentation());
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
