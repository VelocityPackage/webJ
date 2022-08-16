package com.velocitypackage.webJ.services.webJ;

import com.velocitypackage.webJ.materials.hypertext.Bootstrap;
import com.velocitypackage.webJ.materials.hypertext.Style;

public class MessageBuilder
{
    private final String title;
    private final String textRepresentation;
    private final Style[] styles;
    private final Bootstrap[] bootstraps;
    
    /**
     * Constructor
     * @param title the message title
     * @param textRepresentation the message html
     * @param styles the message style
     * @param bootstraps the message classes / bootstrap
     */
    public MessageBuilder(String title, String textRepresentation, Style[] styles, Bootstrap[] bootstraps)
    {
        this.title = title;
        this.textRepresentation = textRepresentation;
        this.styles = styles;
        this.bootstraps = bootstraps;
    }
    
    /**
     * returns the message title
     * @return the message title
     */
    public String getTitle()
    {
        return title;
    }
    
    /**
     * returns the style inert parameter
     * @return the style inert parameter
     */
    public String getStyleInert()
    {
        return styleInterpreter(styles);
    }
    
    /**
     * returns the bootstrap inert parameter
     * @return the bootstrap inert parameter
     */
    public String getBootstrapInert()
    {
        return bootstrapInterpreter(bootstraps);
    }
    
    private static String styleInterpreter(Style[] styles)
    {
        StringBuilder styleInterpretation = new StringBuilder();
        for (Style style : styles)
        {
            styleInterpretation.append(style.getIdentifier().name().toLowerCase().replaceAll("_", "-")).append(":").append(style.getValue()).append(";");
        }
        return new String(styleInterpretation).trim();
    }
    
    private static String bootstrapInterpreter(Bootstrap[] bootstraps)
    {
        StringBuilder bootstrapInterpretation = new StringBuilder();
        for (Bootstrap bootstrap : bootstraps)
        {
            bootstrapInterpretation.append(bootstrap.name().toLowerCase().replaceAll("_", "-")).append(" ");
        }
        return new String(bootstrapInterpretation).trim();
    }
    
    /**
     * Message formatter
     * @return Format (name:{TITLE}&&style:{STYLE}&&classes:{CLASSES}&&hypertext:{HTML})
     */
    @Override
    public String toString()
    {
        return String.format("name:%s&&style:%s&&classes:%s&&hypertext:%s", getTitle(), getStyleInert().trim(), getBootstrapInert().trim(), textRepresentation);
    }
}
