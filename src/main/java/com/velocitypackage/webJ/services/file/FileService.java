package com.velocitypackage.webJ.services.file;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.List;

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
     * resize ico image and returns the resized version as byte array
     * @param image ico file
     * @param h new height
     * @param w new weight
     * @return the resized image bytes
     * @throws IOException thrown if the image does not exist
     * @throws ImageReadException thrown if the image is not readable
     * @throws ImageWriteException thrown if the image is not writable
     */
    public static byte[] resizedIco(File image, int h, int w) throws IOException, ImageReadException, ImageWriteException
    {
        List<BufferedImage> nonScaledImages = Imaging.getAllBufferedImages(image);
        if (nonScaledImages.isEmpty())
        {
           throw new IOException("Image does not exits");
        }
        BufferedImage nonScaledImage = nonScaledImages.get(0);
        Image scaledImage = nonScaledImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        Imaging.writeImage(imageToBufferedImage(scaledImage), byteArrayOutputStream, ImageFormats.ICO);
        return byteArrayOutputStream.toByteArray();
    }
    
    /**
     * convert a .ico file into png bytes
     * @param image ico file
     * @return the png image bytes
     * @throws IOException thrown if the image does not exist
     * @throws ImageReadException thrown if the image is not readable
     */
    public static byte[] icoToPng(File image) throws IOException, ImageReadException
    {
        List<BufferedImage> nonScaledImages = Imaging.getAllBufferedImages(image);
        if (nonScaledImages.isEmpty())
        {
            throw new IOException("Image does not exits");
        }
        BufferedImage nonScaledImage = nonScaledImages.get(0);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        ImageIO.write(nonScaledImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    /**
     * resize ico image and returns the resized version as byte array
     * @param image ico file
     * @param h new height
     * @param w new weight
     * @return the resized image bytes
     * @throws IOException thrown if the image does not exist
     * @throws ImageReadException thrown if the image is not readable
     */
    public static byte[] resizedIcoToPng(File image, int h, int w) throws IOException, ImageReadException
    {
        List<BufferedImage> nonScaledImages = Imaging.getAllBufferedImages(image);
        if (nonScaledImages.isEmpty())
        {
            throw new IOException("Image does not exits");
        }
        BufferedImage nonScaledImage = nonScaledImages.get(0);
        Image scaledImage = nonScaledImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        ImageIO.write(imageToBufferedImage(scaledImage), "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    
    private static BufferedImage imageToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics bg = bufferedImage.getGraphics();
        bg.drawImage(image, 0, 0, null);
        bg.dispose();
        return bufferedImage;
    }
}
