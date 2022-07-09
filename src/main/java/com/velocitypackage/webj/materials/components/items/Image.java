package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

/**
 * @author maxmielchen
 */
public class Image extends Component
{
    private String base64;
    
    /**
     * Creates a basic image
     * @param image image path / file reference
     */
    public Image (File image)
    {
        try
        {
            base64 = toBase64(image);
            setHyperTextElement(new HyperTextElement(Tag.IMG, new Bootstrap[]{Bootstrap.IMG_FLUID}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.SRC, "data:image/webp;base64, " + base64)}, new Style[]{new Style(Style.StyleIdentifier.MAX_WIDTH, "100%"), new Style(Style.StyleIdentifier.HEIGHT, "auto")}));
        } catch (IOException e)
        {
            setHyperTextElement(new HyperTextElement(""));
        }
    }
    
    /**
     * Creates a basic image
     * @param image image path / file reference
     * @param bootstraps bootstrap options
     */
    public Image (File image, Bootstrap... bootstraps)
    {
        try
        {
            base64 = toBase64(image);
            setHyperTextElement(new HyperTextElement(Tag.IMG, combine(new Bootstrap[]{Bootstrap.IMG_FLUID}, bootstraps), new Attribute[]{new Attribute(Attribute.AttributeIdentifier.SRC, "data:image/webp;base64, " + base64)}, new Style[]{new Style(Style.StyleIdentifier.MAX_WIDTH, "100%"), new Style(Style.StyleIdentifier.HEIGHT, "auto")}));
        } catch (IOException e)
        {
            setHyperTextElement(new HyperTextElement(""));
        }
    }
    
    /**
     * Creates a basic image
     * @param image image path / file reference
     * @param styles style options
     */
    public Image (File image, Style... styles)
    {
        try
        {
            base64 = toBase64(image);
            setHyperTextElement(new HyperTextElement(Tag.IMG, new Bootstrap[]{Bootstrap.IMG_FLUID}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.SRC, "data:image/webp;base64, " + base64)}, combine(new Style[]{new Style(Style.StyleIdentifier.MAX_WIDTH, "100%"), new Style(Style.StyleIdentifier.HEIGHT, "auto")}, styles)));
        } catch (IOException e)
        {
            setHyperTextElement(new HyperTextElement(""));
        }
    }
    
    public String getBase64()
    {
        return base64;
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
    
    /**
     * convert a file into a image base64 interpretation
     * @param image file of image
     * @return the base64 version
     * @throws IOException throws if the image didn't exist
     */
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
