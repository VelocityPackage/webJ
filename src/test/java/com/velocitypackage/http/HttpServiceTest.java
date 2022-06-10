package com.velocitypackage.http;


import java.io.IOException;

class HttpServiceTest
{
    
    public static void main(String[] args) throws IOException
    {
        HttpService service = new HttpService(8080);
        service.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                System.out.println(path);
                return true;
            }
            
            @Override
            public String content()
            {
                return "null";
            }
        });
        
        service.start();
    }
}