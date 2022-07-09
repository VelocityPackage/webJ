package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.materials.hypertext.HyperTextPage;

public class Page extends HyperTextPage
{
    public Page(String path, HyperTextBehavior... children)
    {
        super(path, children);
    }
}
