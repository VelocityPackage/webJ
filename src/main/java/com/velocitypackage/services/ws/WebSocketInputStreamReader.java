package com.velocitypackage.services.ws;

import java.io.IOException;
import java.io.InputStream;

public class WebSocketInputStreamReader
{
    private InputStream inputStream;
    private byte[] incomingBuffer;
    private byte[] messageBuffer;
    private byte[] masks;
    private boolean messageSplitOverMultipleRead;
    private int messageLength;
    private int messageReadCount;
    
    public WebSocketInputStreamReader(InputStream webSocketInputStream){
        inputStream = webSocketInputStream;
        incomingBuffer = new byte[8000];
        messageBuffer = null;
        masks = new byte[4];
        messageSplitOverMultipleRead = false;
        messageLength = 0;
        messageReadCount = 0;
        
        Thread thread = new Thread(this::loop);
    }
    
    private void loop(){
        byte[] b = incomingBuffer;
        byte[] message = messageBuffer;
        boolean isSplit = messageSplitOverMultipleRead;
        int length = messageLength;
        int totalRead = messageReadCount;
        while (true) {
            int len = 0;//length of bytes read from socket
            try {
                len = inputStream.read(b);
            } catch (IOException e) {
                break;
            }
            if (len != -1) {
                boolean more = false;
                int totalLength = 0;
                do {
                    int j = 0;
                    int i = 0;
                    if (!isSplit) {
                        byte rLength = 0;
                        int rMaskIndex = 2;
                        int rDataStart = 0;
                        // b[0] assuming text
                        byte data = b[1];
                        byte op = (byte) 127;
                        rLength = (byte) (data & op);
                        length = (int) rLength;
                        if (rLength == (byte) 126) {
                            rMaskIndex = 4;
                            length = Byte.toUnsignedInt(b[2]) << 8;
                            length += Byte.toUnsignedInt(b[3]);
                        } else if (rLength == (byte) 127)
                            rMaskIndex = 10;
                        for (i = rMaskIndex; i < (rMaskIndex + 4); i++) {
                            masks[j] = b[i];
                            j++;
                        }
                    
                        rDataStart = rMaskIndex + 4;
                    
                        message = new byte[length];
                        totalLength = length + rDataStart;
                        for (i = rDataStart, totalRead = 0; i<len && i < totalLength; i++, totalRead++) {
                            message[totalRead] = (byte) (b[i] ^ masks[totalRead % 4]);
                        }
                    
                    }else {
                        for (i = 0; i<len && totalRead<length; i++, totalRead++) {
                            message[totalRead] = (byte) (b[i] ^ masks[totalRead % 4]);
                        }
                        totalLength=i;
                    }
                
                
                    if (totalRead<length) {
                        isSplit=true;
                    }else {
                        isSplit=false;
                        System.out.println(new String(message));
                        b = new byte[8000];
                    }
                
                    if (totalLength < len) {
                        more = true;
                        for (i = totalLength, j = 0; i < len; i++, j++)
                            b[j] = b[i];
                        len = len - totalLength;
                    }else
                        more = false;
                } while (more);
            } else
                break;
        }
    }
}
