package com.velocitypackage.materials.components;

import java.util.Map;

public class Layout extends Component
{
    public Container topLeft;
    public Container topMiddle;
    public Container topRight;
    
    public Container left;
    public Container middle;
    public Container right;
    
    public Container bottomLeft;
    public Container bottomMiddle;
    public Container bottomRight;
    
    public Layout()
    {
        topLeft = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "left");
                super.add(component);
            }
        };
        topLeft.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        topLeft.putStyle(HyperTextElement.STYLE.PADDING, "0px");
        topMiddle = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "auto");
                component.putStyle(HyperTextElement.STYLE.MARGIN_LEFT, "auto");
                super.add(component);
            }
        };
        topMiddle.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        topMiddle.putStyle(HyperTextElement.STYLE.PADDING, "0px");
        topRight = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "right");
                super.add(component);
            }
        };
        topRight.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        topRight.putStyle(HyperTextElement.STYLE.PADDING, "0px");
        left = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "left");
                super.add(component);
            }
        };
        left.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        left.putStyle(HyperTextElement.STYLE.PADDING, "0px");
        middle = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "auto");
                component.putStyle(HyperTextElement.STYLE.MARGIN_LEFT, "auto");
                super.add(component);
            }
        };
        middle.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        middle.putStyle(HyperTextElement.STYLE.PADDING, "0px");
        right = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "right");
                super.add(component);
            }
        };
        right.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        right.putStyle(HyperTextElement.STYLE.PADDING, "0px");
        bottomLeft = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "left");
                super.add(component);
            }
        };
        bottomLeft.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        bottomLeft.putStyle(HyperTextElement.STYLE.PADDING, "0px");
        bottomMiddle = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.MARGIN_RIGHT, "auto");
                component.putStyle(HyperTextElement.STYLE.MARGIN_LEFT, "auto");
                super.add(component);
            }
        };
        bottomMiddle.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        bottomMiddle.putStyle(HyperTextElement.STYLE.PADDING, "0px");
        bottomRight = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "right");
                super.add(component);
            }
        };
        bottomRight.putStyle(HyperTextElement.STYLE.MARGIN, "0px");
        bottomRight.putStyle(HyperTextElement.STYLE.PADDING, "0px");
    }
    
    @Override
    public void add(Component component)
    {
    }
    
    @Override
    public void onInteract(String id, Map<String, String> inputs)
    {
        topLeft.onInteract(id, inputs);
        topMiddle.onInteract(id, inputs);
        topRight.onInteract(id, inputs);
        left.onInteract(id, inputs);
        middle.onInteract(id, inputs);
        right.onInteract(id, inputs);
        bottomLeft.onInteract(id, inputs);
        bottomMiddle.onInteract(id, inputs);
        bottomRight.onInteract(id, inputs);
    }
    
    @Override
    public HyperTextElement getContent()
    {
        StringBuilder contentTop = new StringBuilder();
        contentTop.append(topLeft.getContent());
        contentTop.append(topMiddle.getContent());
        contentTop.append(topRight.getContent());
        HyperTextElement top = new HyperTextElement(HyperTextElement.TAG.DIV, new String(contentTop)); //1. Row
        top.addClass("d-flex", "flex-row");
        StringBuilder contentMiddleVertical = new StringBuilder();
        contentMiddleVertical.append(left.getContent());
        contentMiddleVertical.append(middle.getContent());
        contentMiddleVertical.append(right.getContent());
        HyperTextElement middleVertical = new HyperTextElement(HyperTextElement.TAG.DIV, new String(contentMiddleVertical)); //2. Row
        middleVertical.addClass("d-flex", "flex-row");
        StringBuilder contentBottom = new StringBuilder();
        contentBottom.append(bottomLeft.getContent());
        contentBottom.append(bottomMiddle.getContent());
        contentBottom.append(bottomRight.getContent());
        HyperTextElement bottom = new HyperTextElement(HyperTextElement.TAG.DIV, new String(contentBottom)); //3. Row
        bottom.addClass("d-flex", "flex-row");
        StringBuilder layout = new StringBuilder();
        layout.append(top.compile());
        layout.append(middleVertical.compile());
        layout.append(bottom.compile());
        HyperTextElement container = new HyperTextElement(HyperTextElement.TAG.DIV, new String(layout));
        container.addStyle(HyperTextElement.STYLE.WIDTH, "100vw");
        for (Map.Entry<HyperTextElement.STYLE, String> style : styles.entrySet())
        {
            container.addStyle(style.getKey(), style.getValue());
        }
        for (Bootstrap bootstrapClass : classes)
        {
            container.addClass(bootstrap(bootstrapClass));
        }
        return container;
    }
}
