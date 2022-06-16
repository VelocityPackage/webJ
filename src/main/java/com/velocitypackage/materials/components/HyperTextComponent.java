package com.velocitypackage.materials.components;

import com.velocitypackage.materials.hypertext.HyperTextElement;

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
