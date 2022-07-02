package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
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
        addChild(HyperTextBehavior.fromElement(hyperTextElement));
    }
    
    public Style[] styles()
    {
        return new Style[]{new Style(Style.StyleIdentifier.MARGIN, "10px")};
    }
}
