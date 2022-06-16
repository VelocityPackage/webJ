package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.HyperTextElement;

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
