package com.velocitypackage.materials.components;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class Image implements Component
{
    private String base64Image;
    private final Map<HyperTextElement.STYLE, String> styles;
    
    public Image(File image)
    {
        base64Image = "data:image/webp;base64,";
        try
        {
            base64Image += encodeBase64(image);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        styles = new HashMap<>();
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    @Override
    public void putStyle(HyperTextElement.STYLE option, String value)
    {
        styles.put(option, value);
    }
    
    @Override
    public void onInteract(String id, Map<String, String> inputs)
    {
    }
    
    @Override
    public HyperTextElement getContent()
    {
        HyperTextElement image = new HyperTextElement(HyperTextElement.TAG.IMG);
        image.addClass("img-fluid");
        image.addStyle(HyperTextElement.STYLE.OBJECT_FIT, "cover");
        image.setSrc(base64Image);
        for (HyperTextElement.STYLE s : styles.keySet())
        {
            image.addStyle(s, styles.get(s));
        }
        return image;
    }
    
    public static String encodeBase64(File image) throws IOException
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
}
