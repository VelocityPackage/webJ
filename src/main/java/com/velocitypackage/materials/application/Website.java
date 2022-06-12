package com.velocitypackage.materials.application;

import com.velocitypackage.materials.components.Component;
import com.velocitypackage.materials.components.HyperTextElement;
import com.velocitypackage.materials.components.Page;

public class Website
{
    private Page currentPage;
    
    public Website(){
    
    }
    
    public void setPage(Page p){
        currentPage = p;
    }
    
    public String getHyperText()
    {
        return currentPage.getHyperText();
    }
}
