package com.velocitypackage.webJ.materials.webJ;

import com.velocitypackage.webJ.materials.hypertext.*;
import com.velocitypackage.webJ.tools.TestApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest
{
    HyperTextElement hyperTextElement = new HyperTextElement(Tag.H1, new Bootstrap[]{
            Bootstrap.H1
    }, new Attribute[]{
            new Attribute(Attribute.AttributeIdentifier.ID, "heading")
    }, new Style[]{
            new Style(Style.StyleIdentifier.COLOR, "black")
    });
    
    HyperTextBehavior hyperTextBehavior = new HyperTextBehavior()
    {
        @Override
        public void onInteract(Map<String, String> values)
        {
            System.out.println("onInteract(); -> was calling");
        }
    };
    
    HyperTextPage hyperTextPage;
    Application application;
    
    @BeforeEach
    void setUp()
    {
        hyperTextBehavior.setHyperTextElement(hyperTextElement);
        hyperTextPage = new HyperTextPage("Page", "/", new Style[]{
                new Style(Style.StyleIdentifier.BACKGROUND_COLOR, "black")
        }, new Bootstrap[]{
                Bootstrap.BORDER_DARK
        }, hyperTextBehavior);
        application = new Application()
        {
        };
    }
    
    @AfterEach
    void tearDown()
    {
        hyperTextBehavior.recompile();
    }
    
    @Test
    void addPage()
    {
        application.addPage(hyperTextPage);
        List.of(application.getPages()).forEach(hyperTextPage1 -> assertEquals(hyperTextPage, hyperTextPage1));
    }
    
    @Test
    void setApplicationName()
    {
        application.setApplicationName("Application");
    }
    
    @Test
    void setFavicon()
    {
        application.setFavicon(new File("src/test/resources/favicon.ico"));
    }
    
    @Test
    void setForceUpdate()
    {
        application.setForceUpdate(() -> System.out.println("Hello, World"));
        application.forceUpdate();
    }
    
    @Test
    void getCurrentPageName()
    {
        addPage();
        assertEquals("Page", application.getCurrentPageName());
    }
    
    @Test
    void getCurrentPageRepresentation()
    {
        addPage();
        assertNotEquals(
                "<div class=\"text-center d-block vh-100 position-fixed vw-100\"><hr /><h3 class=\"text-center\">404</h3><p class=\"text-center\">This page doesn't exist...</p><hr /></div>",
                application.getCurrentPageRepresentation());
    }
    
    @Test
    void getCurrentPageStyle()
    {
        addPage();
        assertEquals(new Style[]{new Style(Style.StyleIdentifier.BACKGROUND_COLOR, "black")}, application.getCurrentPageStyle());
    }
    
    @Test
    void getCurrentPageBootstraps()
    {
        addPage();
        assertEquals(new Bootstrap[]{Bootstrap.BORDER_DARK}, application.getCurrentPageBootstraps());
    }
    
    @Test
    void getApplicationName()
    {
        application.setApplicationName("Application");
        assertEquals("Application", application.getApplicationName());
    }
    
    @Test
    void getFavicon()
    {
        application.setFavicon(new File("src/test/resources/favicon.ico"));
        assertEquals("src/test/resources/favicon.ico", application.getFavicon().getPath());
    }
    
    @Test
    void getPages()
    {
        application.getPages();
    }
    
    @Test
    void forceUpdate()
    {
        application.setForceUpdate(() -> System.out.println("forceUpdate"));
        application.forceUpdate();
    }
    
    @Test
    void onMessage()
    {
        // TODO: 15.08.22 #38
    }
    
    @Test
    void testClone() throws CloneNotSupportedException
    {
        assertNotEquals(new TestApplication(), new TestApplication().clone());
    }
}