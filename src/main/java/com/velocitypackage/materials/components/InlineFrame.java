package com.velocitypackage.materials.components;

public class InlineFrame implements Component
{
    private final String link;
    
    public InlineFrame(String link)
    {
        this.link = link;
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    @Override
    public void onClick(String id)
    {
    }
    
    @Override
    public HyperTextElement getHTML()
    {
        return new HyperTextElement(HyperTextElement.TAG.IFRAME)
        {
            @Override
            public String compile()
            {
                return "<iframe src=\"" + link + "\">iFrame not supported</iframe>";
            }
        };
    }
}
