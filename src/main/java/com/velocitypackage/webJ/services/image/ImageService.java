package com.velocitypackage.webJ.services.image;

import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageService
{
    
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
    public static byte[] resizedIco(File image, int h, int w) throws IOException, ImageReadException, ImageWriteException, IllegalArgumentException
    {
        if (h > 256 || w > 256)
        {
            throw new IllegalArgumentException("ico max size == 256");
        }
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
