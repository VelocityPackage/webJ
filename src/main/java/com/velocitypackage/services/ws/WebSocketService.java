package com.velocitypackage.services.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WebSocketService
{
    private final ServerSocket serverSocket;
    private final Socket clientSocket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    
    public WebSocketService(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
        } catch (IOException createException)
        {
            throw new IllegalStateException("Could not create web server", createException);
        }
        try
        {
            clientSocket = serverSocket.accept(); //waits until a client connects
        } catch (IOException waitException)
        {
            throw new IllegalStateException("Could not wait for client connection", waitException);
        }
        try
        {
            inputStream = clientSocket.getInputStream();
        } catch (IOException inputStreamException)
        {
            throw new IllegalStateException("Could not connect to client input stream", inputStreamException);
        }
        try
        {
            outputStream = clientSocket.getOutputStream();
        } catch (IOException outputStreamException)
        {
            throw new IllegalStateException("Could not connect to client output stream", outputStreamException);
        }
        try
        {
            webSocketHandshake(inputStream, outputStream);
        } catch (IOException webSocketHandshakeException)
        {
            throw new IllegalStateException("Websockets handshake was unsuccessful", webSocketHandshakeException);
        }
    }
    
    private void webSocketHandshake(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        
    }
}
