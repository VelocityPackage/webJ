package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

/**
 * @author maxmielchen
 */
public class Heading extends Item
{
    /**
     * Creates a new Heading
     * @param text the heading text
     */
    public Heading(String text)
    {
        setHyperTextElement(new HyperTextElement(Tag.P, new Bootstrap[]{Bootstrap.H3}, null, null, text));
    }
    
    /**
     * Creates a new Heading
     * @param text the heading text
     * @param type heading size type
     */
    public Heading(String text, Type type)
    {
        setHyperTextElement(new HyperTextElement(Tag.P, new Bootstrap[]{type.bootstrap}, null, null, text));
    }
    
    /**
     * Creates a new Heading
     * @param text the heading text
     * @param bootstraps bootstrap options
     */
    public Heading(String text, Bootstrap... bootstraps)
    {
        setHyperTextElement(new HyperTextElement(Tag.P, combine(new Bootstrap[]{Bootstrap.H3}, bootstraps), null, null, text));
    }
    
    /**
     * Creates a new Heading
     * @param text the heading text
     * @param type heading size type
     * @param bootstraps bootstrap options
     */
    public Heading(String text, Type type, Bootstrap... bootstraps)
    {
        setHyperTextElement(new HyperTextElement(Tag.P, combine(new Bootstrap[]{type.bootstrap}, bootstraps), null, null, text));
    }
    
    /**
     * Creates a new Heading
     * @param text the heading text
     * @param styles style options
     */
    public Heading(String text, Style... styles)
    {
        setHyperTextElement(new HyperTextElement(Tag.P, new Bootstrap[]{Bootstrap.H3}, null, styles, text));
    }
    
    /**
     * Creates a new Heading
     * @param text the heading text
     * @param type heading size type
     * @param styles style options
     */
    public Heading(String text, Type type, Style... styles)
    {
        setHyperTextElement(new HyperTextElement(Tag.P, new Bootstrap[]{type.bootstrap}, null, styles, text));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
    
    /**
     * Heading type size
     */
    public enum Type
    {
        H1(Bootstrap.H1), H2(Bootstrap.H2), H3(Bootstrap.H3), H4(Bootstrap.H4), H5(Bootstrap.H5), H6(Bootstrap.H6);
        public final Bootstrap bootstrap;
        Type(Bootstrap bootstrap)
        {
            this.bootstrap = bootstrap;
        }
    }
}
