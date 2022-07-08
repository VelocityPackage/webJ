package com.velocitypackage.webj.materials.hypertext;

import java.util.Objects;

/**
 * Each instance of the Attribute class represents an assignment of a value to one of the html attributes.
 * Objects of the Attribute class are immutable.
 *
 * @author marvinmielchen
 * @author maxmielchen
 */
@SuppressWarnings("unused")
public class Attribute
{
    
    private final AttributeIdentifier attributeIdentifier;
    private final String value;
    
    /**
     * A constructor for the Attribute class that creates a new Attribute object from the given html attribute and a value.
     *
     * @param attributeIdentifier the attribute that the value is assigned to
     * @param value               the value that is assigned to the given html attribute
     */
    public Attribute(AttributeIdentifier attributeIdentifier, String value)
    {
        this.attributeIdentifier = attributeIdentifier;
        this.value = value;
    }
    
    /**
     * Gets the html attribute of the Attribute object.
     *
     * @return the html attribute
     */
    public AttributeIdentifier getIdentifier()
    {
        return attributeIdentifier;
    }
    
    /**
     * Gets the value of the Attribute object that is associated to the html attribute.
     *
     * @return the value of the html attribute
     */
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
        Attribute attribute = (Attribute) o;
        return attributeIdentifier == attribute.attributeIdentifier && value.equals(attribute.value);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(attributeIdentifier, value);
    }
    
    
    /**
     * The AttributeIdentifier enumeration contains all html attributes.
     */
    public enum AttributeIdentifier
    {
        ARIA_HIDDEN, ARIA_MODAL, ACCEPT, ACCEPT_CHARSET, ACCESSKEY, ACTION, ALIGN, ALT, ASYNC, AUTOCOMPLETE, AUTOFOCUS, AUTOPLAY, BGCOLOR, BORDER, CHARSET, CHECKED, CITE, CLASS, COLOR, COLS, COLSPAN, CONTENT, CONTENTEDITABLE, CONTROLS, COORDS, DATA, DATETIME, DEFAULT, DEFER, DIR, DIRNAME, DISABLED, DOWNLOAD, DRAGGABLE, ENCTYPE, FOR, FORM, FORMACTION, HEADERS, HEIGHT, HIDDEN, HIGH, HREF, HREFLANG, HTTP_EQUIV, ID, ISMAP, KIND, LABEL, LANG, LIST, LOOP, LOW, MAX, MAXLENGTH, MEDIA, METHOD, MIN, MULTIPLE, MUTED, NAME, NOVALIDATE, ONABORT, ONAFTERPRINT, ONBEFOREPRINT, ONBEFOREUNLOAD, ONBLUR, ONCANPLAY, ONCANPLAYTHROUGH, ONCHANGE, ONCLICK, ONCONTEXTMENU, ONCOPY, ONCUECHANGE, ONCUT, ONDBLCLICK, ONDRAG, ONDRAGEND, ONDRAGENTER, ONDRAGLEAVE, ONDRAGOVER, ONDRAGSTART, ONDROP, ONDURATIONCHANGE, ONEMPTIED, ONENDED, ONERROR, ONFOCUS, ONHASHCHANGE, ONINPUT, ONINVALID, ONKEYDOWN, ONKEYPRESS, ONKEYUP, ONLOAD, ONLOADEDDATA, ONLOADEDMETADATA, ONLOADSTART, ONMOUSEDOWN, ONMOUSEMOVE, ONMOUSEOUT, ONMOUSEOVER, ONMOUSEUP, ONMOUSEWHEEL, ONOFFLINE, ONONLINE, ONPAGEHIDE, ONPAGESHOW, ONPASTE, ONPAUSE, ONPLAY, ONPLAYING, ONPOPSTATE, ONPROGRESS, ONRATECHANGE, ONRESET, ONRESIZE, ONSCROLL, ONSEARCH, ONSEEKED, ONSEEKING, ONSELECT, ONSTALLED, ONSTORAGE, ONSUBMIT, ONSUSPEND, ONTIMEUPDATE, ONTOGGLE, ONUNLOAD, ONVOLUMECHANGE, ONWAITING, ONWHEEL, OPEN, OPTIMUM, PATTERN, PLACEHOLDER, POSTER, PRELOAD, READONLY, REL, REQUIRED, REVERSED, ROWS, ROWSPAN, SANDBOX, SCOPE, SELECTED, SHAPE, SIZE, SIZES, SPAN, SPELLCHECK, SRC, SRCDOC, SRCLANG, SRCSET, START, STEP, STYLE, TABINDEX, TARGET, TITLE, TRANSLATE, TYPE, USEMAP, VALUE, WIDTH, WRAP
    }
}


