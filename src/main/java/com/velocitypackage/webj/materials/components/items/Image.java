package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.hypertext.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class Image extends Item
{
    public Image (File image)
    {
        try
        {
            setHyperTextElement(new HyperTextElement(Tag.IMG, new Bootstrap[]{Bootstrap.IMG_FLUID}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.SRC, "data:image/webp;base64, " + toBase64(image))}, new Style[]{new Style(Style.StyleIdentifier.MAX_WIDTH, "100%"), new Style(Style.StyleIdentifier.HEIGHT, "auto")}));
        } catch (IOException e)
        {
            setHyperTextElement(new HyperTextElement(""));
        }
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
    
    private String toBase64(File image) throws IOException
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
