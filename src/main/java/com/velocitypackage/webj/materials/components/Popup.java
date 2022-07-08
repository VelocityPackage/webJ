package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.*;

import java.util.Map;

public class Popup extends Component
{
    
    public Popup(Component[] header, Component[] body, Component[] footer)
    {
        openPopup();
        Component dialog = new Panel(
                false,
                new Bootstrap[]{Bootstrap.MODAL_DIALOG, Bootstrap.MODAL_DIALOG_CENTERED},
                new Panel(
                        false,
                        new Bootstrap[]{Bootstrap.MODAL_CONTENT},
                        new Panel(
                                false,
                                new Bootstrap[]{Bootstrap.MODAL_HEADER},
                                header
                        ),
                        new Panel(
                                false,
                                new Bootstrap[]{Bootstrap.MODAL_BODY},
                                body
                        ),
                        new Panel(
                                false,
                                new Bootstrap[]{Bootstrap.MODAL_FOOTER},
                                footer
                        )
                )
        );
        addChild(dialog);
    }
    
    public void openPopup()
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{
                Bootstrap.MODAL,
                Bootstrap.FADE,
                Bootstrap.SHOW
        }, new Attribute[]{
                new Attribute(Attribute.AttributeIdentifier.TABINDEX, "-1"),
                new Attribute(Attribute.AttributeIdentifier.ARIA_MODAL, "true")
        }, new Style[]{
                new Style(Style.StyleIdentifier.DISPLAY, "block"),
                new Style(Style.StyleIdentifier.BACKDROP_FILTER, "blur(2px)"),
                new Style(Style.StyleIdentifier.BACKGROUND_COLOR, "rgba(0,0,0,0.5)")
        }));
    }
    
    public void closePopup()
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{
                Bootstrap.MODAL,
                Bootstrap.FADE
        }, new Attribute[]{
                new Attribute(Attribute.AttributeIdentifier.TABINDEX, "-1"),
                new Attribute(Attribute.AttributeIdentifier.ARIA_HIDDEN, "true")
        }, new Style[]{
                new Style(Style.StyleIdentifier.DISPLAY, "none"),
        }));
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
