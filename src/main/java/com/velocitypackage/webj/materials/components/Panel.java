package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class Panel extends Component
{
    public Panel()
    {
        setHyperTextElement(new HyperTextElement(Tag.DIV, null, null, null));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    
    }
}
