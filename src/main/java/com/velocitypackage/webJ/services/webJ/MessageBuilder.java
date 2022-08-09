package com.velocitypackage.webJ.services.webJ;

import com.velocitypackage.webJ.materials.hypertext.Bootstrap;
import com.velocitypackage.webJ.materials.hypertext.Style;

public class MessageBuilder
{
    private final String textRepresentation;
    private final Style[] styles;
    private final Bootstrap[] bootstraps;
    
    public MessageBuilder(String textRepresentation, Style[] styles, Bootstrap[] bootstraps)
    {
        this.textRepresentation = textRepresentation;
        this.styles = styles;
        this.bootstraps = bootstraps;
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
        return new String(styleInterpretation);
    }
    
    private static String bootstrapInterpreter(Bootstrap[] bootstraps)
    {
        StringBuilder bootstrapInterpretation = new StringBuilder();
        for (Bootstrap bootstrap : bootstraps)
        {
            bootstrapInterpretation.append(bootstrap.name().toLowerCase().replaceAll("_", "-")).append(" ");
        }
        return new String(bootstrapInterpretation);
    }
    
    @Override
    public String toString()
    {
        return String.format("style:%s bootstrap:%s hypertext:%s", getStyleInert().trim(), getBootstrapInert().trim(), textRepresentation);
    }
}
