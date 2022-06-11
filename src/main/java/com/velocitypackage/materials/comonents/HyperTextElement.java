package com.velocitypackage.materials.comonents;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class HyperTextElement
{
    private final TAG tag;
    private final String content;
    private final List<String> classes = new ArrayList<>();
    private final List<String> ids = new ArrayList<>();
    private final Map<STYLE, String> styles = new HashMap<>();
    
    public HyperTextElement(TAG tag)
    {
        this.tag = tag;
        content = "";
    }
    
    public HyperTextElement(TAG tag, String content)
    {
        this.tag = tag;
        this.content = content;
    }
    
    public void addClass(String... classes)
    {
        this.classes.addAll(List.of(classes));
    }
    
    public void addId(String... ids)
    {
        this.ids.addAll(List.of(ids));
    }
    
    public void addStyle(STYLE style, String value)
    {
        this.styles.put(style, value);
    }
    
    public void addStyle(STYLE style, int value)
    {
        this.styles.put(style, String.valueOf(value));
    }
    
    public String compile()
    {
        StringBuilder classes = new StringBuilder();
        for (String cl : this.classes)
        {
            classes.append(cl).append(" ");
        }
        String classesForTag = "class=\"" + new String(classes).trim() + "\"";
        StringBuilder ids = new StringBuilder();
        for (String id : this.ids)
        {
            ids.append(id).append(" ");
        }
        String idsForTag = "id=\"" + new String(ids).trim() + "\"";
        StringBuilder styles = new StringBuilder();
        for (Map.Entry<STYLE, String> entry : this.styles.entrySet())
        {
            styles.append(styleToString(entry.getKey())).append(": ").append(entry.getValue()).append(";");
        }
        String stylesForTag = "style=\"" + new String(styles).trim() + "\"";
        String starts = "<" + tagToString(tag) + " " + classesForTag + " " + idsForTag + " " + stylesForTag + ">";
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
        BACKGROUND, BACKGROUND_ATTACHMENT, BACKGROUND_COLOR, BACKGROUND_IMAGE, BACKGROUND_POSITION, BACKGROUND_REPEAT, BORDER, BORDER_BOTTOM, BORDER_BOTTOM_COLOR, BORDER_BOTTOM_STYLE, BORDER_BOTTOM_WIDTH, BORDER_COLOR, BORDER_LEFT, BORDER_LEFT_COLOR, BORDER_LEFT_STYLE, BORDER_LEFT_WIDTH, BORDER_RIGHT, BORDER_RIGHT_COLOR, BORDER_RIGHT_STYLE, BORDER_RIGHT_WIDTH, BORDER_STYLE, BORDER_TOP, BORDER_TOP_COLOR, BORDER_TOP_STYLE, BORDER_TOP_WIDTH, BORDER_WIDTH, CLEAR, CLIP, COLOR, CURSOR, DISPLAY, FILTER, FLOAT, FONT, FONT_FAMILY, FONT_SIZE, FONT_VARIANT, FONT_WEIGHT, HEIGHT, LEFT, LETTER_SPACING, LINE_HEIGHT, LIST_STYLE, LIST_STYLE_IMAGE, LIST_STYLE_POSITION, LIST_STYLE_TYPE, MARGIN, MARGIN_BOTTOM, MARGIN_LEFT, MARGIN_RIGHT, MARGIN_TOP, OVERFLOW, PADDING, PADDING_BOTTOM, PADDING_LEFT, PADDING_RIGHT, PADDING_TOP, PAGE_BREAK_AFTER, PAGE_BREAK_BEFORE, POSITION, STROKE_DASHARRAY, STROKE_DASHOFFSET, TEXT_ALIGN, TEXT_DECORATION, TEXT_INDENT, TEXT_TRANSFORM, TOP, VERTICAL_ALIGN, VISIBILITY, WIDTH, Z_INDEX
    }
}
