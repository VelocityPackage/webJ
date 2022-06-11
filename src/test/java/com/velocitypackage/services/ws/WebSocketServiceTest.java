package com.velocitypackage.services.ws;

import com.velocitypackage.materials.components.Component;
import com.velocitypackage.materials.components.Header;
import com.velocitypackage.materials.components.HyperTextElement;
import com.velocitypackage.materials.components.Page;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.nio.ByteBuffer;

class WebSocketServiceTest extends WebSocketServer
{
    public WebSocketServiceTest()
    {
        super(8080);
    }
    
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
    
        Page page = new Page();
        Header header = new Header();
        Header.Item item = new Header.Item("Base", "header", true)
        {
            @Override
            public void onClick()
            {
                super.onClick(id);
            }
        };
    }
    
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }
    
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
    }
    
    @Override
    public void onMessage( WebSocket conn, ByteBuffer message ) {
        System.out.println("received ByteBuffer from "	+ conn.getRemoteSocketAddress());
    }
    
    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
    }
    
    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }
    
    
    public static void main(String[] args) {
        WebSocketServer server = new WebSocketServiceTest();
        server.run();
    }
}