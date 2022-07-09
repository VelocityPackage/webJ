package com.velocitypackage.webj.materials.hypertext;

import java.util.Map;

public class HyperTextPage
{
    private final String path;
    private final HyperTextBehavior[] children;
    
    public HyperTextPage(String path, HyperTextBehavior... children)
    {
        this.path = path;
        this.children = children;
    }
    
    public String getTextRepresentation()
    {
        StringBuilder pageInterpretation = new StringBuilder();
        for (HyperTextBehavior child : children) {
            pageInterpretation.append(child.getTextRepresentation());
        }
        return new String(pageInterpretation);
    }
    
    public String getPath()
    {
        return path;
    }
    
    public void onMessage(String id, Map<String, String> values)
    {
        for (HyperTextBehavior hyperTextBehavior : children)
        {
            hyperTextBehavior.onMessage(id, values);
        }
    }
}
