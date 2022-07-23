package com.velocitypackage.webJ.services.http;

import org.webbitserver.HttpControl;
import org.webbitserver.HttpHandler;
import org.webbitserver.HttpRequest;
import org.webbitserver.HttpResponse;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class HttpService implements HttpHandler
{
    private final Set<HttpContext> httpContexts = new HashSet<>();
    private final Set<HttpFileContext> httpFileContexts = new HashSet<>();
    
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
    
    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception
    {
        String path = request.uri();
    
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
                    
                    response.header("Content-Type", httpContext.contentType())
                            .content(content)
                            .end();
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
                    byte[] content = httpFileContext.content(path);
                    response.header("Content-Type", httpFileContext.contentType())
                            .content(content)
                            .end();
                    break;
                }
            }
        }
        if (isNull)
        {
            control.nextHandler();
        }
    }
}
