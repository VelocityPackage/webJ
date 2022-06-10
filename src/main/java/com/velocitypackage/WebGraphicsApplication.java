package com.velocitypackage;

import com.velocitypackage.http.HttpContext;
import com.velocitypackage.http.HttpService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WebGraphicsApplication
{
    public static void main(String[] args) throws IOException
    {
        HttpService service = new HttpService(8080);
        
        service.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                return path.equals("/");
            }
            
            @Override
            public String content()
            {
                try
                {
                    System.out.println(this.getClass().getResource("frame.html").getContent());
                    
                    String content = Files.readString(Path.of(this.getClass().getResource("frame.html").getPath()));
                    
                    System.out.println("Start");
                    System.out.println(content);
                    System.out.println("End");
                    
                    return content;
                } catch (IOException e)
                {
                    return "null";
                }
            }
        });
        
        service.add(new HttpContext()
        {
            @Override
            public boolean acceptPath(String path)
            {
                return path.equals("/stream.js") || path.equals("/stream");
            }
            
            @Override
            public String content()
            {
                try
                {
                    return Files.readString(Path.of(this.getClass().getResource("stream.js").getPath()));
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
        
        service.start();
    }
}
