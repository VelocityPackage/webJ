package com.velocitypackage.materials.components;

public class Link implements Component
{
    private final String link;
    private final String name;
    
    public Link(String link, String name)
    {
        this.link = link;
        this.name = name;
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
    public HyperTextElement getContent()
    {
        return new HyperTextElement(HyperTextElement.TAG.IFRAME)
        {
            @Override
            public String compile()
            {
                return "<a style=\"margin: 10px;\" href=\"" + link + "\">" + name + "</a>";
            }
        };
    }
}
