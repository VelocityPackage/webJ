package com.velocitypackage.services.http;

/**
 * HttpContext like "/index" or "/home"
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
     * return of content
     *
     * @return content
     */
    String content();
}
