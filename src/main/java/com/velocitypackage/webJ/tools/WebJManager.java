package com.velocitypackage.webJ.tools;

import com.velocitypackage.webJ.materials.webJ.Application;
import com.velocitypackage.webJ.materials.webJ.NotSupportedMessageFormatException;
import com.velocitypackage.webJ.services.file.FileService;
import com.velocitypackage.webJ.services.http.HttpContext;
import com.velocitypackage.webJ.services.http.HttpFileContext;
import com.velocitypackage.webJ.services.http.HttpService;
import com.velocitypackage.webJ.services.image.ImageService;
import com.velocitypackage.webJ.services.webJ.MessageBuilder;
import com.velocitypackage.webJ.services.ws.WebSocketService;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public final class WebJManager
{
    private HttpService httpService;
    private WebSocketService webSocketService;
    private final WebServer webServer;
    
    private final Map<WebSocketConnection, Application> connections;
    
    private final int port;
    private final Application application;
    
    private String frameHtmlResource, streamJsResource, robotsTxtResource;
    
    private final HashMap<Integer, byte[]> faviconCacheIco;
    private final HashMap<Integer, byte[]> faviconCachePng;
    private byte[] faviconIco;
    private byte[] faviconPng;
    
    /**
     * Creates a Server service management system for a specific application
     * @param port port of webpage
     * @param application the application
     * @throws IOException throws the port is the same
     */
    public WebJManager(int port, Application application) throws IOException
    {
        this.connections = new HashMap<>();
        this.faviconCacheIco = new HashMap<>();
        this.faviconCachePng = new HashMap<>();
        this.port = port;
        this.application = application;
        this.webServer = WebServers.createWebServer(this.port);
        this.httpServiceSetup();
        this.webSocketServiceSetup();
        this.webServer.add(httpService);
        this.webServer.add("/socket", webSocketService);
    }
    
    /**
     * starts all services
     */
    @SuppressWarnings("unused")
    public void start()
    {
        webServer.start();
        System.out.println("Webserver listening on port " + webServer.getPort());
        System.out.println("http://localhost:" + port);
        try
        {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("http://" + localhost.getHostAddress() + ":" + port);
            System.out.println("http://" + localhost.getHostName() + ":" + port);
        } catch (Exception ignore) {}
    }
    
    /**
     * stops all services
     */
    @SuppressWarnings("unused")
    public void stop()
    {
        webServer.stop();
        System.out.println("Server was successfully closed...");
    }
    
    /**
     * sets up the http service
     */
    private void httpServiceSetup() throws IOException
    {
        httpService = new HttpService();
        frameHtmlResource = FileService.getContentOfResource("frame.html")
                .replaceFirst("%NAME%", application.getApplicationName());
        frameHtmlResource = faviconBase64ServiceSetup(frameHtmlResource);
        httpService.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                for (String notAllowed : new String[]{"/stream", "/robots.txt", "/favicon.ico", "/favicon.png", "/socket"})
                {
                    if (Objects.equals(path, notAllowed))
                    {
                        return false;
                    }
                }
                return (!path.startsWith("/favicon.ico") && !path.startsWith("/favicon.png"));
            }
    
            @Override
            public String contentType()
            {
                return "text/html";
            }
    
            @Override
            public String content(String path)
            {
                return frameHtmlResource;
            }
        });
        streamJsResource = FileService.getContentOfResource("stream.js");
        httpService.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                return path.equals("/stream");
            }
    
            @Override
            public String contentType()
            {
                return "text/javascript";
            }
    
            @Override
            public String content(String path)
            {
                return streamJsResource;
            }
        });
        robotsTxtResource = FileService.getContentOfResource("robots.txt");
        httpService.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                return path.equals("/robots.txt");
            }
    
            @Override
            public String contentType()
            {
                return "text/plain";
            }
    
            @Override
            public String content(String path)
            {
                return robotsTxtResource;
            }
        });
        faviconServiceSetup();
    }
    
    /**
     * Replace all favicons throw base64
     * @param frameHtmlResource source
     * @return new source
     */
    private String faviconBase64ServiceSetup(String frameHtmlResource) throws IOException
    {
        if (application.getFavicon() != null)
        {
            frameHtmlResource = frameHtmlResource.replaceAll("%FAVICON%", FileService.toBase64(application.getFavicon()));
        } else
        {
            frameHtmlResource = frameHtmlResource.replaceAll("%FAVICON%", "");
        }
        return frameHtmlResource;
    }
    
    /**
     * Setting up the favicons
     * @throws IOException don't throw
     */
    private void faviconServiceSetup() throws IOException
    {
        if (application.getFavicon() != null)
        {
            faviconIco = Files.readAllBytes(application.getFavicon().toPath());
            httpService.add(new HttpFileContext()
            {
                @Override
                public boolean acceptPath(String path)
                {
                    return path.startsWith("/favicon.ico");
                }
            
                @Override
                public String contentType()
                {
                    return "image/x-icon";
                }
            
                @Override
                public byte[] content(String path)
                {
                    String[] pathParameter = path.split("\\?");
                    if (pathParameter.length == 1)
                    {
                        return faviconIco;
                    } else
                    {
                        int size = Integer.parseInt(pathParameter[1]);
                        if (size > 256)
                        {
                            return new byte[]{-1};
                        }
                        if (faviconCacheIco.get(size) == null)
                        {
                            try
                            {
                                faviconCacheIco.put(size, ImageService.resizedIco(application.getFavicon(), size, size));
                                return faviconCacheIco.get(size);
                            } catch (IOException | ImageReadException | ImageWriteException e)
                            {
                                throw new RuntimeException(e);
                            }
                        } else
                        {
                            return faviconCacheIco.get(size);
                        }
                    }
                }
            });
            try
            {
                faviconPng = ImageService.icoToPng(application.getFavicon());
            } catch (ImageReadException e)
            {
                throw new IOException(e);
            }
            httpService.add(new HttpFileContext()
            {
                @Override
                public boolean acceptPath(String path)
                {
                    return path.startsWith("/favicon.png");
                }
        
                @Override
                public String contentType()
                {
                    return "image/png";
                }
        
                @Override
                public byte[] content(String path)
                {
                    String[] pathParameter = path.split("\\?");
                    if (pathParameter.length == 1)
                    {
                        return faviconPng;
                    } else
                    {
                        int size = Integer.parseInt(pathParameter[1]);
                        if (faviconCachePng.get(size) == null)
                        {
                            try
                            {
                                faviconCachePng.put(size, ImageService.resizedIcoToPng(application.getFavicon(), size, size));
                                return faviconCachePng.get(size);
                            } catch (IOException | ImageReadException e)
                            {
                                throw new RuntimeException(e);
                            }
                        } else
                        {
                            return faviconCachePng.get(size);
                        }
                    }
                }
            });
        }
    }
    
    /**
     * sets up the web socket service
     */
    private void webSocketServiceSetup()
    {
        webSocketService = new WebSocketService()
        {
            @Override
            public void onOpen(WebSocketConnection connection)
            {
                try
                {
                    connections.put(connection, application.clone());
                } catch (CloneNotSupportedException e)
                {
                    e.printStackTrace();
                }
                connections.get(connection).setForceUpdate(() -> connection.send(new MessageBuilder(
                        connections.get(connection).getTextRepresentation(),
                        connections.get(connection).getStyle(),
                        connections.get(connection).getBootstrap()
                ).toString())
                ); //force update
            }
    
            @Override
            public void onClose(WebSocketConnection connection)
            {
                connections.remove(connection);
            }
    
            @Override
            public void onMessage(WebSocketConnection connection, String message)
            {
                try
                {
                    connections.get(connection).onMessage(message);
                } catch (NotSupportedMessageFormatException e)
                {
                    e.printStackTrace();
                }
                connection.send(new MessageBuilder(
                        connections.get(connection).getTextRepresentation(),
                        connections.get(connection).getStyle(),
                        connections.get(connection).getBootstrap()
                ).toString());
            }
        };
    }
}
