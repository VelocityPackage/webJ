package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

/**
 * @author maxmielchen
 */
public class ButtonGroup extends Component
{
    
    /**
     * holder of a button toolbar / group
     * @param buttons buttons (not convert)
     */
    public ButtonGroup(Button... buttons)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.BTN_GROUP}, null, new Style[]{new Style(Style.StyleIdentifier.MARGIN, "10px")}));
        for (Button button : buttons)
        {
            super.addChild(Button.convertButtonGroup(button));
        }
    }
    
    /**
     * holder of a button toolbar / group
     * @param bootstraps boostrap options
     * @param buttons buttons (not convert)
     */
    public ButtonGroup(Bootstrap[] bootstraps, Button... buttons)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, combine(new Bootstrap[]{Bootstrap.BTN_GROUP}, bootstraps), null, new Style[]{new Style(Style.StyleIdentifier.MARGIN, "10px")}));
        for (Button button : buttons)
        {
            super.addChild(Button.convertButtonGroup(button));
        }
    }
    
    /**
     * holder of a button toolbar / group
     * @param styles style options
     * @param buttons buttons (not convert)
     */
    public ButtonGroup(Style[] styles, Button... buttons)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.BTN_GROUP}, null, combine(new Style[]{new Style(Style.StyleIdentifier.MARGIN, "10px")}, styles)));
        for (Button button : buttons)
        {
            super.addChild(Button.convertButtonGroup(button));
        }
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
