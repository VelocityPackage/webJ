package com.velocitypackage.webj.materials.components;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextElement;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.materials.hypertext.Tag;

import java.util.Map;

/**
 * @author maxmielchen
 */
public class Row extends Component
{
    
    /**
     * Creates a row and put all children side by side
     *
     * @param children Varargs of child Components
     */
    public Row(Component... children)
    {
        super.setHyperTextElement(new HyperTextElement(Tag.DIV, new Bootstrap[]{Bootstrap.ROW}, null, new Style[]{new Style(Style.StyleIdentifier.__BS_GUTTER_X, "")}));
        if (children == null)
        {
            return;
        }
        for (Component child : children)
        {
            addChild(new Column(child));
        }
    }
    
    @Override
    public void onInteract(Map<String, String> values)
    {
    }
}
