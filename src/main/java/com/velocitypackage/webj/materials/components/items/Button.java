package com.velocitypackage.webj.materials.components.items;

import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

/**
 * @author maxmielchen
 */
public class Button extends Item
{
    public final String text;
    public final Runnable runnable;
    public final ButtonType buttonType;
    
    /**
     * Creates a button
     *
     * @param text inner text of button´
     * @param r runnable when the button is pressed
     */
    public Button(String text, Runnable r)
    {
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
        if (buttonType == null) {
            buttonType = ButtonType.PRIMARY;
        }
        setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, buttonType.state}, null, styles, text));
        this.text = text;
        this.runnable = r;
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
        Component component = new Component()
        {
            @Override
            public void onInteract(Map<String, String> values)
            {
                button.runnable.run();
            }
        };
        component.setHyperTextElement(new HyperTextElement(Tag.BUTTON, new Bootstrap[]{Bootstrap.BTN, button.buttonType.state}, null, null, button.text));
        return component;
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
        runnable.run();
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
