package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.*;

import java.util.Map;

public class Button extends HyperTextBehavior
{
    public Button()
    {
        Tuple<Attribute, String> attributeStringTuple = new Tuple<>(Attribute.TYPE, "button");
        Tuple<Attribute, String>[] arr = new Tuple[]{attributeStringTuple};
        
        setContent(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, new Tuple[]{attributeStringTuple}, null));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
        System.out.println("Click!");
    }
}
