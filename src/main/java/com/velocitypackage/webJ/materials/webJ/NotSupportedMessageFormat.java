package com.velocitypackage.webJ.materials.webJ;

import java.util.Arrays;

/**
 * Exception for unformatted messages or not supported formatted message
 * @author maxmielchen
 */
@SuppressWarnings("unused")
public class NotSupportedMessageFormat extends Exception
{
    /**
     * Constructor
     */
    public NotSupportedMessageFormat()
    {
        super();
    }
    
    /**
     * Constructor
     * @param format supported format as array / varargs -> IMPORTANT: It's not a format check
     */
    public NotSupportedMessageFormat(String... format)
    {
        super("Supported Formats: " + Arrays.toString(format));
    }
}
