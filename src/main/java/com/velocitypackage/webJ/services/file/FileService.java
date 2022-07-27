package com.velocitypackage.webJ.services.file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * This is the utility for files
 *
 * @author maxmielchen
 */
public class FileService
{
    /**
     * gets a file from the resource folder and read the internals
     *
     * @param path path in the resource folder
     * @return content of resource
     * @throws IOException          if the file did not exist
     * @throws NullPointerException if the file is empty
     */
    public static String getContentOfResource(String path) throws IOException, NullPointerException
    {
        if (path == null)
        {
            return null;
        }
        try
        {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            StringBuilder content = new StringBuilder();
            try (InputStream inputStream = classloader.getResourceAsStream(path))
            {
                if (inputStream == null)
                {
                    throw new NullPointerException("File reader is null");
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                for (String line; (line = reader.readLine()) != null; )
                {
                    content.append(line).append("\n");
                }
            }
            return new String(content);
        } catch (Exception e)
        {
            throw new IOException("File did not exist");
        }
    }
    
    /**
     * Convert a file into a image base64 interpretation
     * @param image file of image
     * @return the base64 version
     * @throws IOException throws if the image didn't exist
     */
    public static String toBase64(File image) throws IOException
    {
        FileInputStream stream = new FileInputStream(image);
        int bufLength = 2048;
        byte[] buffer = new byte[2048];
        byte[] data;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int readLength;
        while ((readLength = stream.read(buffer, 0, bufLength)) != - 1)
        {
            out.write(buffer, 0, readLength);
        }
        data = out.toByteArray();
        String imageString = Base64.getEncoder().withoutPadding().encodeToString(data);
        out.close();
        stream.close();
        return imageString;
    }
    
    /**
     * A stable methode to read a file
     *
     * @param file the file
     * @return file content
     */
    @SuppressWarnings("unused")
    public static String readString(File file) {
        try
        {
            StringBuilder content = new StringBuilder();
            BufferedReader fileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = fileBufferedReader.readLine()) != null) {
                content.append(line);
            }
            fileBufferedReader.close();
            return new String(content);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * A stable methode to resize images
     * @param img
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static byte[] resizeImage(byte[] img, int width, int height) throws IOException
    {
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(img));
        
        Image tmp = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(resized, "png", os);
        os.flush();
        os.close();
        return os.toByteArray();
    }
}
