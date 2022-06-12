package com.velocitypackage.materials.application;

import com.velocitypackage.materials.components.Component;
import com.velocitypackage.materials.components.HyperTextElement;
import com.velocitypackage.materials.components.Page;

public class AppRoot
{
    private Page currentPage;
    
    public AppRoot(){
        currentPage = new Page();
    }
    
    public void onClick(String id){
        currentPage.onClick(id);
    }
    
    public void setPage(Page p){
        currentPage = p;
    }
    
    public String getHyperText()
    {
        return currentPage.getHyperText();
    }
}
