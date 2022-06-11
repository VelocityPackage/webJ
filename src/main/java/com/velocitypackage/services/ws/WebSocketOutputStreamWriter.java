package com.velocitypackage.services.ws;

import java.io.IOException;
import java.io.OutputStream;

public class WebSocketOutputStreamWriter
{
    private final OutputStream outputStream;
    
    public WebSocketOutputStreamWriter(OutputStream webSocketOutputStream){
        outputStream = webSocketOutputStream;
    }
    
    public void writeLine(String message) throws IOException
    {
        outputStream.write(encodeWebSocketMessage(message));
        outputStream.flush();
    }
    
    private byte[] encodeWebSocketMessage(String message){
        byte[] rawData = message.getBytes();
        int frameCount  = 0;
        byte[] frame = new byte[10];
        frame[0] = (byte) 129;
        if(rawData.length <= 125){
            frame[1] = (byte) rawData.length;
            frameCount = 2;
        }
        if(rawData.length > 125 && rawData.length <= 65535){
            frame[1] = (byte) 126;
            int len = rawData.length;
            frame[2] = (byte)((len >> 8 ) & (byte)255);
            frame[3] = (byte)(len & (byte)255);
            frameCount = 4;
        }
        if(rawData.length > 65535){
            frame[1] = (byte) 127;
            long len = rawData.length;
            frame[2] = (byte)(0);
            frame[3] = (byte)(0);
            frame[4] = (byte)(0);
            frame[5] = (byte)(0);
            frame[6] = (byte)((len >> 24 ) & (byte)255);
            frame[7] = (byte)((len >> 16 ) & (byte)255);
            frame[8] = (byte)((len >> 8 ) & (byte)255);
            frame[9] = (byte)(len & (byte)255);
            frameCount = 10;
        }
        int bLength = frameCount + rawData.length;
        byte[] reply = new byte[bLength];
        int bLim = 0;
        for(int i=0; i < frameCount; i++){
            reply[bLim] = frame[i];
            bLim++;
        }
        for (byte rawDatum : rawData)
        {
            reply[bLim] = rawDatum;
            bLim++;
        }
        return reply;
    }
}
