package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;

@SuppressWarnings("unused")
public enum Align
{
    START(Bootstrap.JUSTIFY_CONTENT_START, Bootstrap.ALIGN_ITEMS_START), MIDDLE(Bootstrap.JUSTIFY_CONTENT_CENTER, Bootstrap.ALIGN_ITEMS_CENTER), END(Bootstrap.JUSTIFY_CONTENT_END, Bootstrap.ALIGN_ITEMS_END);
    public final Bootstrap horizontal;
    public final Bootstrap vertical;
    Align(Bootstrap horizontal, Bootstrap vertical)
    {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }
}
