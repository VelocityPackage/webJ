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
        
        topRight = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "right");
                super.add(component);
            }
        };
        
        left = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "left");
                super.add(component);
            }
        };
        
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
        
        right = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "right");
                super.add(component);
            }
        };
        
        bottomLeft = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "left");
                super.add(component);
            }
        };
        
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
        
        bottomRight = new Container()
        {
            @Override
            public void add(Component component)
            {
                component.putStyle(HyperTextElement.STYLE.FLOAT, "right");
                super.add(component);
            }
        };
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
        contentTop.append(topLeft);
        contentTop.append(topMiddle);
        contentTop.append(topRight);
        HyperTextElement top = new HyperTextElement(HyperTextElement.TAG.DIV, new String(contentTop)); //1. Row
        top.addClass("row", "row-cols-3");
        StringBuilder contentMiddleVertical = new StringBuilder();
        contentMiddleVertical.append(left);
        contentMiddleVertical.append(middle);
        contentMiddleVertical.append(right);
        HyperTextElement middleVertical = new HyperTextElement(HyperTextElement.TAG.DIV, new String(contentMiddleVertical)); //2. Row
        middleVertical.addClass("row", "row-cols-3");
        StringBuilder contentBottom = new StringBuilder();
        contentBottom.append(bottomLeft);
        contentBottom.append(bottomMiddle);
        contentBottom.append(bottomRight);
        HyperTextElement bottom = new HyperTextElement(HyperTextElement.TAG.DIV, new String(contentBottom)); //3. Row
        bottom.addClass("row", "row-cols-3");
        StringBuilder layout = new StringBuilder();
        layout.append(contentTop);
        layout.append(contentMiddleVertical);
        layout.append(contentBottom);
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
