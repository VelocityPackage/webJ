package com.velocitypackage.webj.services.http;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/**
 * Service for http contexts
 *
 * @author maxmielchen
 */
@SuppressWarnings("unused")
public class HttpService
{
    private final HttpServer server;
    
    private final Set<HttpContext> httpContexts = new HashSet<>();
    private final Set<HttpFileContext> httpFileContexts = new HashSet<>();
    
    /**
     * Constructor for Webservice
     *
     * @param port with them, you can connect to the HttpService
     * @throws IOException if the port is already in usage
     */
    public HttpService(int port) throws IOException
    {
        try
        {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException ignored)
        {
            throw new IOException("Port is already in use");
        }
    }
    
    /**
     * Adds a new context
     *
     * @param httpContext context self
     */
    public void add(HttpContext httpContext)
    {
        if (httpContext == null)
        {
            return;
        }
        httpContexts.add(httpContext);
    }
    
    /**
     * remove specific context
     *
     * @param httpContext context self
     */
    public void remove(HttpContext httpContext)
    {
        if (httpContext == null)
        {
            return;
        }
        httpContexts.remove(httpContext);
    }
    
    /**
     * Adds a new context
     *
     * @param httpFileContext context self
     */
    public void add(HttpFileContext httpFileContext)
    {
        if (httpFileContext == null)
        {
            return;
        }
        httpFileContexts.add(httpFileContext);
    }
    
    /**
     * remove specific context
     *
     * @param httpFileContext context self
     */
    public void remove(HttpFileContext httpFileContext)
    {
        if (httpFileContext == null)
        {
            return;
        }
        httpFileContexts.remove(httpFileContext);
    }
    
    /**
     * start the http service in another thread
     */
    public void start()
    {
        new Thread(this::run).start();
    }
    
    /**
     * runnable
     */
    private void run()
    {
        server.createContext("/", exchange ->
        {
            String path = exchange.getRequestURI().getPath();
            
            boolean isNull = true;
            if (isNull) {
                for (HttpContext httpContext : httpContexts)
                {
                    if (httpContext.acceptPath(path))
                    {
                        isNull = false;
                        String content = httpContext.content(path);
            
                        //NEXT FOR UPDATES ->
                        //content = content.replaceAll("\n", " ").replaceAll("\t", " ").trim();
    
                        exchange.getResponseHeaders().add("Content-Type", httpContext.contentType());
                        exchange.sendResponseHeaders(200, content.getBytes().length);
                        OutputStream output = exchange.getResponseBody();
                        output.write(content.getBytes());
                        output.flush();
                        output.close();
                        break;
                    }
                }
            }
            if (isNull) {
                for (HttpFileContext httpFileContext : httpFileContexts)
                {
                    if (httpFileContext.acceptPath(path))
                    {
                        isNull = false;
                        byte[] content = Files.readAllBytes(httpFileContext.content(path).toPath());
                        exchange.getResponseHeaders().add("Content-Type", httpFileContext.contentType());
                        exchange.sendResponseHeaders(200, content.length);
                        OutputStream output = exchange.getResponseBody();
                        output.write(content);
                        output.flush();
                        output.close();
                        break;
                    }
                }
            }
            if (isNull)
            {
                exchange.sendResponseHeaders(405, - 1);
            }
        });
        server.setExecutor(null);
        server.start();
    }
}
