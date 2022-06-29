package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.materials.hypertext.Style;

public abstract class Component extends HyperTextBehavior
{
    public Style[] combine(Style[] styles1, Style[] styles2)
    {
        Style[] combine = new Style[styles1.length + styles2.length];
        System.arraycopy(styles1, 0, combine, 0, styles1.length);
        System.arraycopy(styles2, 0, combine, styles1.length, styles2.length);
        return combine;
    }
}
