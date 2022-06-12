package com.velocitypackage.tools;

import com.velocitypackage.materials.components.Page;

import java.util.Random;

public abstract class WebGraphicsApplication
{
    private Page currentPage;
    
    private Random random;
    
    public final void setPage(Page page){
        this.currentPage = page;
    }
    
    public final void onMessage(String message) {
        currentPage.onClick(message.trim());
    }
    
    
}
