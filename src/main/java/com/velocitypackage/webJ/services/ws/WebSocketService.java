package com.velocitypackage.webJ.services.ws;

import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebSocketConnection;

/**
 * Wrapper for BaseWebSocketHandler
 * @author maxmielchen
 */
public abstract class WebSocketService extends BaseWebSocketHandler
{
    @Override
    public abstract void onOpen(WebSocketConnection connection);
    
    @Override
    public abstract void onClose(WebSocketConnection connection);
    
    @Override
    public abstract void onMessage(WebSocketConnection connection, String msg);
}
