package com.velocitypackage.webj.materials.components;

public class HyperTextComponent extends Component
{
    private final HyperTextElement element;
    
    public HyperTextComponent(HyperTextElement element)
    {
        this.element = element;
    }
    
    @Override
    public HyperTextElement getHyperText()
    {
        return element;
    }
}
