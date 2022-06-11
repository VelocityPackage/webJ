package com.velocitypackage.services.ws;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

/**
 *  Each instance of the WebSocketService fulfills the role of a server that is listening for web-socket connections.
 *  It can handle multiple clients and send and receive data via the established websockets.
 *
 * @author marvinmielchen
 */
@SuppressWarnings("unused")
public class WebSocketService
{
    private final ServerSocket serverSocket;
    private final List<WebSocket> clientList;
    
    public WebSocketService(int port)
    {
        clientList = new ArrayList<>();
        try
        {
            serverSocket = new ServerSocket(port);
        } catch (IOException createException)
        {
            throw new IllegalStateException("Could not create web-socket server-socket", createException);
        }
        new Thread(()->{
            try
            {
                clientList.add(waitForWebSocketAccept(serverSocket));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();
    }
    
    public WebSocket waitForWebSocketAccept(ServerSocket serverSocket) throws IOException
    {
        Socket clientSocket = serverSocket.accept();
        return new WebSocket(clientSocket);
    }
    
}
