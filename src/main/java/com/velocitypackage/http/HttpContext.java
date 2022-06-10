package com.velocitypackage.http;

public interface HttpContext
{
    boolean acceptPath(String path);
    
    String content();
}
