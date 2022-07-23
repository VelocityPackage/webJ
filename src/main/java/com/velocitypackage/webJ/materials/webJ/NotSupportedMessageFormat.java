package com.velocitypackage.webJ.materials.webJ;

import java.util.Arrays;

@SuppressWarnings("unused")
public class NotSupportedMessageFormat extends Exception
{
    public NotSupportedMessageFormat()
    {
        super();
    }
    
    public NotSupportedMessageFormat(String... format)
    {
        super("Supported Formats: " + Arrays.toString(format));
    }
}
