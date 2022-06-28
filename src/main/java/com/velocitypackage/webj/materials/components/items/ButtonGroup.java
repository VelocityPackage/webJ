package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class ButtonGroup extends Item
{
    public ButtonGroup(Button... buttons)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.BTN_GROUP}, null, styles()));
        for (Button button : buttons)
        {
            button.setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, button.buttonType.state}, null, null, button.text));
            super.addChild(button);
        }
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    
    }
}
