package com.velocitypackage.services.ws;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String webSocketKey = null;
        while (true){
            String line = bufferedReader.readLine();
            if (line.startsWith("Sec-WebSocket-Key:")){
                webSocketKey = line.replace("Sec-WebSocket-Key: ", "").trim();
            }
            if(line.equals("")){
                break;
            }
        }
        if(webSocketKey == null){
            return;
        }
        String webSocketAccept = generateSha1Hash(webSocketKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
        outputStream.write(("HTTP/1.1 101 Switching Protocols" + "\r\n").getBytes());
        outputStream.write(("Upgrade: websocket"+ "\r\n").getBytes());
        outputStream.write(("Connection: Upgrade"+ "\r\n").getBytes());
        outputStream.write(("Sec-WebSocket-Accept: " + webSocketAccept + "\r\n" + "\r\n").getBytes());
        outputStream.flush();
    }
    
    private String generateSha1Hash(String string){
        String result = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string.getBytes(StandardCharsets.UTF_8));
            result = Base64.getEncoder().encodeToString(crypt.digest());
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
