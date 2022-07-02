package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

public class ButtonGroup extends Item
{
    public ButtonGroup(Button... buttons)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.BTN_GROUP}, null, null));
        for (Button button : buttons)
        {
            super.addChild(Component.fromElement(
                    new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, button.buttonType.state}, null, null, button.text)
            ));
        }
    }
    
    public ButtonGroup(Bootstrap[] bootstraps, Button... buttons)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.BTN_GROUP}, null, null));
        for (Button button : buttons)
        {
            super.addChild(Component.fromElement(
                    new HyperTextElement(Tag.BUTTON, combine(new Bootstrap[]{Bootstrap.BTN, button.buttonType.state}, bootstraps), null, null, button.text)
            ));
        }
    }
    
    public ButtonGroup(Style[] styles, Button... buttons)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.BTN_GROUP}, null, null));
        for (Button button : buttons)
        {
            super.addChild(Component.fromElement(
                    new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, button.buttonType.state}, null, styles, button.text)
            ));
        }
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    
    }
}
