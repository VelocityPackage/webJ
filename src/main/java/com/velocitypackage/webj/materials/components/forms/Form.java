package com.velocitypackage.webj.materials.components.forms;

import com.velocitypackage.webj.materials.components.Align;
import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.components.Panel;
import com.velocitypackage.webj.materials.hypertext.*;

import java.util.Map;

public class Form extends Component
{
    private final Attribute[] attributes = new Attribute[]{new Attribute(Attribute.AttributeIdentifier.ACTION, "javascript:void(0);")};
    private final Bootstrap[] d_flex = new Bootstrap[]{Bootstrap.D_FLEX};
    private final Style[] styles = new Style[]{new Style(Style.StyleIdentifier.HEIGHT, "100%")};
    private final FormEvent formEvent;
    
    public Form(FormEvent event, Component... children)
    {
        formEvent = event;
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, null, attributes, this.styles));
        if (children == null)
        {
            return;
        }
        addChild(new Panel(children));
    }
    
    public Form(FormEvent event, Align horizontal, Align vertical, Component... children)
    {
        formEvent = event;
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, super.combine(d_flex, new Bootstrap[]{horizontal.horizontal, vertical.vertical}), attributes, this.styles));
        if (children == null)
        {
            return;
        }
        addChild(new Panel(children));
    }
    
    public Form(FormEvent event, Bootstrap[] bootstraps, Component... children)
    {
        formEvent = event;
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, bootstraps, attributes, this.styles));
        if (children == null)
        {
            return;
        }
        addChild(new Panel(children));
    }
    
    public Form(FormEvent event, Bootstrap[] bootstraps, Align horizontal, Align vertical, Component... children)
    {
        formEvent = event;
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, super.combine(super.combine(bootstraps, this.d_flex), new Bootstrap[]{horizontal.horizontal, vertical.vertical}), attributes, styles));
        if (children == null)
        {
            return;
        }
        addChild(new Panel(children));
    }
    
    public Form(FormEvent event, Style[] styles, Component... children)
    {
        formEvent = event;
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, null, attributes, super.combine(styles, this.styles)));
        if (children == null)
        {
            return;
        }
        addChild(new Panel(children));
    }
    
    public Form(FormEvent event, Style[] styles, Align horizontal, Align vertical, Component... children)
    {
        formEvent = event;
        super.setHyperTextElement(new HyperTextElement(Tag.FORM, super.combine(this.d_flex, new Bootstrap[]{horizontal.horizontal, vertical.vertical}), attributes, combine(this.styles, styles)));
        if (children == null)
        {
            return;
        }
        addChild(new Panel(children));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
        formEvent.values = values;
        formEvent.run();
    }
}
