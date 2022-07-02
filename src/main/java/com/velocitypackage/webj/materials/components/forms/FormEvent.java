package com.velocitypackage.webj.materials.components.forms;

import java.util.Map;

public abstract class FormEvent
{
    public  Map<String, String> values;
    public abstract void run();
    public final String getInput(Input input)
    {
        return values.get(input.id);
    }
}
