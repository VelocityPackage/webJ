package com.velocitypackage.materials.components;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class interprets an HTML element
 *
 * @author maxmielchen
 */
@SuppressWarnings("unused")
public class HyperTextElement
{
    private final TAG tag;
    private final String content;
    private final List<String> classes = new ArrayList<>();
    private final List<String> ids = new ArrayList<>();
    private String src = null;
    private final Map<STYLE, String> styles = new HashMap<>();
    
    /**
     * Creates element without content
     *
     * @param tag tag like BUTTON, DIV or H1
     */
    public HyperTextElement(TAG tag)
    {
        this.tag = tag;
        content = "";
    }
    
    /**
     * Create element with content
     *
     * @param tag     tag like BUTTON, DIV, or H1
     * @param content content
     */
    public HyperTextElement(TAG tag, String content)
    {
        this.tag = tag;
        this.content = content;
    }
    
    /**
     * adds some classes to the element
     *
     * @param classes classes
     */
    public void addClass(String... classes)
    {
        this.classes.addAll(List.of(classes));
    }
    
    /**
     * adds some ids to the element
     *
     * @param ids ids
     */
    public void addId(String... ids)
    {
        this.ids.addAll(List.of(ids));
    }
    
    /**
     * set src to element
     *
     * @param src src
     */
    public void setSrc(String src)
    {
        this.src = src;
    }
    
    /**
     * append or reset some style options
     *
     * @param style option name
     * @param value option value
     */
    public void addStyle(STYLE style, String value)
    {
        this.styles.put(style, value);
    }
    
    /**
     * append or reset some style options
     *
     * @param style option name
     * @param value option value
     */
    public void addStyle(STYLE style, int value)
    {
        this.styles.put(style, String.valueOf(value));
    }
    
    /**
     * compiles the element to a string(content)
     *
     * @return compiled element
     */
    public String compile()
    {
        String classesForTag = "";
        if (this.classes.size() > 0)
        {
            StringBuilder classes = new StringBuilder();
            for (String cl : this.classes)
            {
                classes.append(cl).append(" ");
            }
            classesForTag = " class=\"" + new String(classes).trim() + "\"";
        }
        String idsForTag = "";
        if (this.ids.size() > 0)
        {
            StringBuilder ids = new StringBuilder();
            for (String id : this.ids)
            {
                ids.append(id).append(" ");
            }
            idsForTag = " id=\"" + new String(ids).trim() + "\"";
        }
        String srcForTag = "";
        if (src != null)
        {
            srcForTag = " src=\"" + src + "\"";
        }
        String stylesForTag = "";
        if (this.styles.size() > 0)
        {
            StringBuilder styles = new StringBuilder();
            for (Map.Entry<STYLE, String> entry : this.styles.entrySet())
            {
                styles.append(styleToString(entry.getKey())).append(": ").append(entry.getValue()).append(";");
            }
            stylesForTag = " style=\"" + new String(styles).trim() + "\"";
        }
        String starts = "<" + tagToString(tag) + classesForTag + idsForTag + srcForTag + stylesForTag + ">";
        String ends = "</" + tagToString(tag) + ">";
        return starts + content + ends;
    }
    
    @Override
    public String toString()
    {
        return compile();
    }
    
    private String tagToString(@NotNull TAG tag)
    {
        return tag.name().toLowerCase();
    }
    
    private String styleToString(@NotNull STYLE style)
    {
        return style.name().toLowerCase().replaceAll("_", "-");
    }
    
    public enum TAG
    {
        A, ABBR, ACRONYM, ADDRESS, APPLET, AREA, ARTICLE, ASIDE, AUDIO, B, BASE, BASEFONT, BDI, BDO, BIG, BLOCKQUOTE, BODY, BR, BUTTON, CANVAS, CAPTION, CENTER, CITE, CODE, COL, COLGROUP, DATA, DATALIST, DD, DEL, DETAILS, DFN, DIALOG, DIR, DIV, DL, DT, EM, EMBED, FIELDSET, FIGCAPTION, FIGURE, FONT, FOOTER, FORM, FRAME, FRAMESET, H1, H2, H3, H4, H5, H6, HEAD, HEADER, HR, HTML, I, IFRAME, IMG, INPUT, INS, KBD, LABEL, LEGEND, LI, LINK, MAIN, MAP, MARK, META, METER, NAV, NOFRAMES, NOSCRIPT, OBJECT, OL, OPTGROUP, OPTION, OUTPUT, P, PARAM, PICTURE, PRE, PROGRESS, Q, RP, RT, RUBY, S, SAMP, SCRIPT, SECTION, SELECT, SMALL, SOURCE, SPAN, STRIKE, STRONG, STYLE, SUB, SUMMARY, SUP, SVG, TABLE, TBODY, TD, TEMPLATE, TEXTAREA, TFOOT, TH, THEAD, TIME, TITLE, TR, TRACK, TT, U, UL, VAR, VIDEO, WBR
    }
    
    public enum STYLE
    {
        BACKGROUND, BACKGROUND_ATTACHMENT, BACKGROUND_COLOR, BACKGROUND_IMAGE, BACKGROUND_POSITION, BACKGROUND_REPEAT, BORDER, BORDER_BOTTOM, BORDER_BOTTOM_COLOR, BORDER_BOTTOM_STYLE, BORDER_BOTTOM_WIDTH, BORDER_COLOR, BORDER_LEFT, BORDER_LEFT_COLOR, BORDER_LEFT_STYLE, BORDER_LEFT_WIDTH, BORDER_RIGHT, BORDER_RIGHT_COLOR, BORDER_RIGHT_STYLE, BORDER_RIGHT_WIDTH, BORDER_STYLE, BORDER_TOP, BORDER_TOP_COLOR, BORDER_TOP_STYLE, BORDER_TOP_WIDTH, BORDER_WIDTH, CLEAR, CLIP, COLOR, CURSOR, DISPLAY, FILTER, FLOAT, FONT, FONT_FAMILY, FONT_SIZE, FONT_VARIANT, FONT_WEIGHT, HEIGHT, LEFT, LETTER_SPACING, LINE_HEIGHT, LIST_STYLE, LIST_STYLE_IMAGE, LIST_STYLE_POSITION, LIST_STYLE_TYPE, MARGIN, MARGIN_BOTTOM, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, OVERFLOW, PADDING, PADDING_BOTTOM, PADDING_LEFT, PADDING_RIGHT, PADDING_TOP, PAGE_BREAK_AFTER, PAGE_BREAK_BEFORE, POSITION, STROKE_DASHARRAY, STROKE_DASHOFFSET, TEXT_ALIGN, TEXT_DECORATION, TEXT_INDENT, TEXT_TRANSFORM, TOP, VERTICAL_ALIGN, VISIBILITY, WIDTH, Z_INDEX, BOX_SHADOW
    }
}
