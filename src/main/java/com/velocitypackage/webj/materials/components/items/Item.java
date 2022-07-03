package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

public abstract class Item extends Component
{
    
    @Override
    public void setHyperTextElement(HyperTextElement hyperTextElement)
    {
        super.setHyperTextElement(
                new HyperTextElement(Tag.DIV, null, null, styles())
        );
        super.id = hyperTextElement.getId();
        addChild(Component.fromElement(hyperTextElement));
    }
    
    /**
     * Basic item style options
     * @return a basic array of item style options
     */
    public Style[] styles()
    {
        return new Style[]{new Style(Style.StyleIdentifier.MARGIN, "10px")};
    }
}
