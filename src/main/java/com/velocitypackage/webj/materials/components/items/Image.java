package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class Image extends Item
{
    public Image (File image)
    {
        setHyperTextElement(new HyperTextElement(Tag.IMG, null, null, null));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    
    }
    
    private String toBase64(File image) throws IOException
    {
        FileInputStream fileInputStreamReader = new FileInputStream(image);
        byte[] bytes = new byte[(int) image.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.getDecoder().decode(Base64.getEncoder().withoutPadding().encodeToString(bytes)), StandardCharsets.UTF_8);
    }
}
