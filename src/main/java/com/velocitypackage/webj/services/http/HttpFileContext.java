package com.velocitypackage.webj.services.http;

public interface HttpFileContext
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
    byte[] content(String path);
}
