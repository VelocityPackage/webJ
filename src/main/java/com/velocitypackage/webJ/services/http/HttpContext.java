package com.velocitypackage.webJ.services.http;

/**
 * HttpContext like "/index" or "/home"
 *
 * @author maxmielchen
 */
public interface HttpContext
{
    /**
     * gives the path as param and can decide
     *
     * @param path the path
     * @return if the path excepted
     */
    boolean acceptPath(String path);
    
    /**
     * returns the type of content as html tag
     *
     * @return content type
     */
    String contentType();
    
    /**
     * return of content
     *
     * @return content
     */
    String content(String path);
}
