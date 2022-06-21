package com.velocitypackage.webj.materials.hypertext;

import java.util.Objects;

public class Style
{
    
    private final StyleIdentifier styleIdentifier;
    private final String value;
    
    public Style(StyleIdentifier styleIdentifier, String value)
    {
        this.styleIdentifier = styleIdentifier;
        this.value = value;
    }
    
    public StyleIdentifier getIdentifier()
    {
        return styleIdentifier;
    }
    
    public String getValue()
    {
        return value;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Style style = (Style) o;
        return styleIdentifier == style.styleIdentifier && value.equals(style.value);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(styleIdentifier, value);
    }
    
    public enum StyleIdentifier
    {
        ALIGN_CONTENT, BACKGROUND, BACKGROUND_ATTACHMENT, BACKGROUND_COLOR, BACKGROUND_IMAGE, BACKGROUND_POSITION, BACKGROUND_REPEAT, BORDER, BORDER_BOTTOM, BORDER_BOTTOM_COLOR, BORDER_BOTTOM_STYLE, BORDER_BOTTOM_WIDTH, BORDER_COLOR, BORDER_LEFT, BORDER_LEFT_COLOR, BORDER_LEFT_STYLE, BORDER_LEFT_WIDTH, BORDER_RIGHT, BORDER_RIGHT_COLOR, BORDER_RIGHT_STYLE, BORDER_RIGHT_WIDTH, BORDER_STYLE, BORDER_TOP, BORDER_TOP_COLOR, BORDER_TOP_STYLE, BORDER_TOP_WIDTH, BORDER_WIDTH, CLEAR, CLIP, COLOR, CURSOR, DISPLAY, FILTER, FLOAT, FONT, FONT_FAMILY, FONT_SIZE, FONT_VARIANT, FONT_WEIGHT, HEIGHT, LEFT, LETTER_SPACING, LINE_HEIGHT, LIST_STYLE, LIST_STYLE_IMAGE, LIST_STYLE_POSITION, LIST_STYLE_TYPE, MARGIN, MARGIN_BOTTOM, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, OVERFLOW, PADDING, PADDING_BOTTOM, PADDING_LEFT, PADDING_RIGHT, PADDING_TOP, PAGE_BREAK_AFTER, PAGE_BREAK_BEFORE, POSITION, STROKE_DASHARRAY, STROKE_DASHOFFSET, TEXT_ALIGN, TEXT_DECORATION, TEXT_INDENT, TEXT_TRANSFORM, TOP, VERTICAL_ALIGN, VISIBILITY, WIDTH, Z_INDEX, BOX_SHADOW, OBJECT_FIT, FLEX_DIRECTION, JUSTIFY_CONTENT, ALIGN_ITEMS
    }
}

