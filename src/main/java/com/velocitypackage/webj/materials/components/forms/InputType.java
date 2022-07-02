package com.velocitypackage.webj.materials.components.forms;

import com.velocitypackage.webj.materials.hypertext.Bootstrap;

public enum InputType
{
    BUTTON(Bootstrap.FORM_CONTROL), CHECKBOX(Bootstrap.FORM_CONTROL), COLOR(Bootstrap.FORM_CONTROL), DATE(Bootstrap.FORM_CONTROL), EMAIL(Bootstrap.FORM_CONTROL), FILE(Bootstrap.FORM_CONTROL), IMAGE(Bootstrap.FORM_CONTROL), MONTH(Bootstrap.FORM_CONTROL), NUMBER(Bootstrap.FORM_CONTROL), PASSWORD(Bootstrap.FORM_CONTROL), RADIO(Bootstrap.FORM_CONTROL), RANGE(Bootstrap.FORM_CONTROL), RESET(Bootstrap.FORM_CONTROL), SEARCH(Bootstrap.FORM_CONTROL), SUBMIT(Bootstrap.FORM_CONTROL), TEL(Bootstrap.FORM_CONTROL), TEXT(Bootstrap.FORM_CONTROL), TIME(Bootstrap.FORM_CONTROL), URL(Bootstrap.FORM_CONTROL), WEEK(Bootstrap.FORM_CONTROL);
    
    public final Bootstrap[] bootstraps;
    InputType(Bootstrap... bootstraps)
    {
        this.bootstraps = bootstraps;
    }
    
    @Override
    public String toString()
    {
        return super.name().toLowerCase().replaceAll("_","-");
    }
}
