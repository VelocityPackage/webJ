package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class TestComp extends HyperTextBehavior
{
    public TestComp()
    {
        setContent(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.BG_DARK}, null, null));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    
    }
}
