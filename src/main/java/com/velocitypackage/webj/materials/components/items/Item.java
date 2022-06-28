package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.Style;

public abstract class Item extends Component
{
    public Style[] styles()
    {
        return new Style[]{new Style(Style.StyleIdentifier.MARGIN, "10px")};
    }
}
