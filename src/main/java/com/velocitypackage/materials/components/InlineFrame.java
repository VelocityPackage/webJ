package com.velocitypackage.materials.components;

public class InlineFrame extends Component
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
    public HyperTextElement getContent()
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
