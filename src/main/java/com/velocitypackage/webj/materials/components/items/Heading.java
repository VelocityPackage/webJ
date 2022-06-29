package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class Heading extends Item
{
    public Heading(String h)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, new Bootstrap[]{Bootstrap.H3}, null, styles(), h));
    }
    
    public Heading(String h, Type type)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, new Bootstrap[]{type.bootstrap}, null, styles(), h));
    }
    
    public Heading(String h, Bootstrap... bootstraps)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, combine(new Bootstrap[]{Bootstrap.H3}, bootstraps), null, null, h));
    }
    
    public Heading(String h, Type type, Bootstrap... bootstraps)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, combine(new Bootstrap[]{type.bootstrap}, bootstraps), null, null, h));
    }
    
    public Heading(String h, Style... styles)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, new Bootstrap[]{Bootstrap.H3}, null, styles, h));
    }
    
    public Heading(String h, Type type, Style... styles)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.P, new Bootstrap[]{type.bootstrap}, null, styles, h));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
    
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
