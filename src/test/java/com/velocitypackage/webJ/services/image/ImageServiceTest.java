package com.velocitypackage.webJ.services.image;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageServiceTest
{
    File image = new File("src/test/resources/favicon.ico");
    
    @Test
    void resizedIco() throws ImageWriteException, IOException, ImageReadException
    {
        System.out.println(Arrays.toString(ImageService.resizedIco(image, 1, 1)));
        assertEquals("[0, 0, 1, 0, 1, 0, 1, 1, 2, 0, 1, 0, 1, 0, 56, 0, 0, 0, 22, 0, 0, 0, 40, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 56, 67, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]", Arrays.toString(ImageService.resizedIco(image, 1, 1)));
    }
    
    @Test
    void icoToPng() throws IOException, ImageReadException
    {
        System.out.println(Arrays.toString(ImageService.icoToPng(image)));
    }
    
    @Test
    void resizedIcoToPng() throws IOException, ImageReadException
    {
        System.out.println(Arrays.toString(ImageService.resizedIcoToPng(image, 1, 1)));
        System.out.println(System.getProperty("java.version"));
        if (!System.getProperty("java.version").startsWith("1.8"))
        {
            assertEquals("[-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 1, 0, 0, 0, 1, 8, 6, 0, 0, 0, 31, 21, -60, -119, 0, 0, 0, 13, 73, 68, 65, 84, 120, 94, 99, -48, 112, -74, -8, 3, 0, 2, -38, 1, -96, 53, -120, -25, 80, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126]", Arrays.toString(ImageService.resizedIcoToPng(image, 1, 1)));
        }
        assertEquals("[-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 1, 0, 0, 0, 1, 8, 6, 0, 0, 0, 31, 21, -60, -119, 0, 0, 0, 13, 73, 68, 65, 84, 120, -38, 99, -48, 112, -74, -8, 3, 0, 2, -38, 1, -96, 86, 73, -77, -49, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126]", Arrays.toString(ImageService.resizedIcoToPng(image, 1, 1)));
    }
}