package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.*;

import java.util.Map;

/**
 * @author maxmielchen
 */
public class Button extends Item
{
    public final String text;
    public final ButtonType buttonType;
    
    public final InteractType interactType;
    public Runnable runnable;
    public String href;
    
    /**
     * Creates a button
     *
     * @param text inner text of button´
     * @param r runnable when the button is pressed
     */
    public Button(String text, Runnable r)
    {
        this.interactType = InteractType.RUNNABLE;
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, null, null, text));
        this.text = text;
        this.runnable = r;
        this.buttonType = ButtonType.PRIMARY;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param buttonType bootstrap type of button
     * @param r runnable when the button is pressed
     */
    public Button(String text, ButtonType buttonType, Runnable r)
    {
        this.interactType = InteractType.RUNNABLE;
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, buttonType.state}, null, null, text));
        this.text = text;
        this.runnable = r;
        this.buttonType = buttonType;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param bootstraps bootstrap option of the button
     * @param r runnable when the button is pressed
     */
    public Button(String text, Bootstrap[] bootstraps , Runnable r)
    {
        this.interactType = InteractType.RUNNABLE;
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, combine(new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, bootstraps), null, null, text));
        this.text = text;
        this.runnable = r;
        this.buttonType = ButtonType.PRIMARY;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param bootstraps bootstrap option of the button
     * @param buttonType bootstrap type of button
     * @param r runnable when the button is pressed
     */
    public Button(String text, Bootstrap[] bootstraps, ButtonType buttonType, Runnable r)
    {
        this.interactType = InteractType.RUNNABLE;
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, combine(new Bootstrap[]{Bootstrap.BTN, buttonType.state}, bootstraps), null, null, text));
        this.text = text;
        this.runnable = r;
        this.buttonType = buttonType;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param styles style option of the button
     * @param r runnable when the button is pressed
     */
    public Button(String text, Style[] styles , Runnable r)
    {
        this.interactType = InteractType.RUNNABLE;
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, null, styles, text));
        this.text = text;
        this.runnable = r;
        this.buttonType = ButtonType.PRIMARY;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param styles style option of the button
     * @param buttonType bootstrap type of button
     * @param r runnable when the button is pressed
     */
    public Button(String text, Style[] styles, ButtonType buttonType, Runnable r)
    {
        this.interactType = InteractType.RUNNABLE;
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, buttonType.state}, null, styles, text));
        this.text = text;
        this.runnable = r;
        this.buttonType = buttonType;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button´
     */
    public Button(String text, HyperTextPage page)
    {
        this.interactType = InteractType.HREF;
        this.href = page.getPath();
        setHyperTextElement(new HyperTextElement(Tag.A, new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page.getPath())}, null, text));
        this.text = text;
        this.buttonType = ButtonType.PRIMARY;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param buttonType bootstrap type of button
     */
    public Button(String text, ButtonType buttonType, HyperTextPage page)
    {
        this.interactType = InteractType.HREF;
        this.href = page.getPath();
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, buttonType.state}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page.getPath())}, null, text));
        this.text = text;
        this.buttonType = buttonType;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param bootstraps bootstrap option of the button
     */
    public Button(String text, Bootstrap[] bootstraps , HyperTextPage page)
    {
        this.interactType = InteractType.HREF;
        this.href = page.getPath();
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, combine(new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, bootstraps), new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page.getPath())}, null, text));
        this.text = text;
        this.buttonType = ButtonType.PRIMARY;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param bootstraps bootstrap option of the button
     * @param buttonType bootstrap type of button
     */
    public Button(String text, Bootstrap[] bootstraps, ButtonType buttonType, HyperTextPage page)
    {
        this.interactType = InteractType.HREF;
        this.href = page.getPath();
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, combine(new Bootstrap[]{Bootstrap.BTN, buttonType.state}, bootstraps), new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page.getPath())}, null, text));
        this.text = text;
        this.buttonType = buttonType;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param styles style option of the button
     */
    public Button(String text, Style[] styles , HyperTextPage page)
    {
        this.interactType = InteractType.HREF;
        this.href = page.getPath();
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page.getPath())}, styles, text));
        this.text = text;
        this.buttonType = ButtonType.PRIMARY;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param styles style option of the button
     * @param buttonType bootstrap type of button
     */
    public Button(String text, Style[] styles, ButtonType buttonType, HyperTextPage page)
    {
        this.interactType = InteractType.HREF;
        this.href = page.getPath();
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, buttonType.state}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page.getPath())}, styles, text));
        this.text = text;
        this.buttonType = buttonType;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button´
     */
    public Button(String text, String page)
    {
        this.interactType = InteractType.HREF;
        this.href = page;
        setHyperTextElement(new HyperTextElement(Tag.A, new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page)}, null, text));
        this.text = text;
        this.buttonType = ButtonType.PRIMARY;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param buttonType bootstrap type of button
     */
    public Button(String text, ButtonType buttonType, String page)
    {
        this.interactType = InteractType.HREF;
        this.href = page;
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.A, new Bootstrap[]{Bootstrap.BTN, buttonType.state}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page)}, null, text));
        this.text = text;
        this.buttonType = buttonType;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param bootstraps bootstrap option of the button
     */
    public Button(String text, Bootstrap[] bootstraps , String page)
    {
        this.interactType = InteractType.HREF;
        this.href = page;
        setHyperTextElement(new HyperTextElement(Tag.A, combine(new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, bootstraps), new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page)}, null, text));
        this.text = text;
        this.buttonType = ButtonType.PRIMARY;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param bootstraps bootstrap option of the button
     * @param buttonType bootstrap type of button
     */
    public Button(String text, Bootstrap[] bootstraps, ButtonType buttonType, String page)
    {
        this.interactType = InteractType.HREF;
        this.href = page;
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.A, combine(new Bootstrap[]{Bootstrap.BTN, buttonType.state}, bootstraps), new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page)}, null, text));
        this.text = text;
        this.buttonType = buttonType;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param styles style option of the button
     */
    public Button(String text, Style[] styles, String page)
    {
        this.interactType = InteractType.HREF;
        this.href = page;
        setHyperTextElement(new HyperTextElement(Tag.A, new Bootstrap[]{Bootstrap.BTN, Bootstrap.BTN_PRIMARY}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page)}, styles, text));
        this.text = text;
        this.buttonType = ButtonType.PRIMARY;
    }
    
    /**
     * Creates a button
     *
     * @param text inner text of button
     * @param styles style option of the button
     * @param buttonType bootstrap type of button
     */
    public Button(String text, Style[] styles, ButtonType buttonType, String page)
    {
        this.interactType = InteractType.HREF;
        this.href = page;
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.A, new Bootstrap[]{Bootstrap.BTN, buttonType.state}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, page)}, styles, text));
        this.text = text;
        this.buttonType = buttonType;
    }
    
    /**
     * Convert the button to a button usable in a button group.
     *
     * @param button the button reference
     * @return the new button for the button group
     */
    public static Component convertButtonGroup(Button button)
    {
        switch (button.interactType)
        {
            case RUNNABLE:
                Component runnableComponent = new Component()
                {
                    @Override
                    public void onInteract(Map<String, String> values)
                    {
                        button.runnable.run();
                    }
                };
                runnableComponent.setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, button.buttonType.state}, null, null, button.text));
                return runnableComponent;
            case HREF:
                Component hrefComponent = new Component()
                {
                    @Override
                    public void onInteract(Map<String, String> values)
                    {
                    }
                };
                hrefComponent.setHyperTextElement(new HyperTextElement(Tag.A, new Bootstrap[]{Bootstrap.BTN, button.buttonType.state}, new Attribute[]{new Attribute(Attribute.AttributeIdentifier.HREF, button.href)}, null, button.text));
                return hrefComponent;
        }
        Component NULL = new Component()
        {
            @Override
            public void onInteract(Map<String, String> values)
            {
        
            }
        };
        NULL.setHyperTextElement(new HyperTextElement(""));
        return NULL;
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
        if (interactType == InteractType.RUNNABLE)
        {
            runnable.run();
        }
    }
    
    private enum InteractType{
        RUNNABLE, HREF
    }
    
    public enum ButtonType
    {
        PRIMARY(Bootstrap.BTN_PRIMARY), SECONDARY(Bootstrap.BTN_SECONDARY), SUCCESS(Bootstrap.BTN_SUCCESS), DANGER(Bootstrap.BTN_DANGER), WARNING(Bootstrap.BTN_WARNING), INFO(Bootstrap.BTN_INFO), LIGHT(Bootstrap.BTN_LIGHT), DARK(Bootstrap.BTN_DARK), LINK(Bootstrap.BTN_LINK), NONE(Bootstrap.BTN);
        public final Bootstrap state;
        ButtonType(Bootstrap state)
        {
            this.state = state;
        }
    }
}
