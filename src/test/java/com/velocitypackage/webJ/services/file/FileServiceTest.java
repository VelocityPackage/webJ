package com.velocitypackage.webJ.services.file;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest
{
    
    @Test
    void getContentOfResource() throws IOException
    {
        String content = FileService.getContentOfResource("favicon.ico");
        System.out.println(content);
        assertNotEquals(null, content);
    }
    
    @Test
    void toBase64() throws IOException
    {
        String base64 = FileService.toBase64(new File("src/test/resources/favicon.ico"));
        System.out.println(base64);
        assertNotEquals(null, base64);
    }
    
    @Test
    void readString()
    {
        String content = FileService.readString(new File("src/test/resources/favicon.ico"));
        System.out.println(content);
        assertNotEquals(null, content);
    }
}