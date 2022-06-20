import com.velocitypackage.webj.materials.hypertext.*;
import com.velocitypackage.webj.tools.WebJApplication;

import java.io.IOException;
import java.util.Map;

public class TestApp extends WebJApplication
{
    
    @Override
    public HyperTextBehavior build()
    {
        HyperTextElement hyperTextElement = new HyperTextElement(Tag.DIV);
        hyperTextElement.addBootstrap(Bootstrap.BG_DARK);
        hyperTextElement.addBootstrap(Bootstrap.TEXT_LIGHT);
        HyperTextBehavior hyperTextBehavior = new HyperTextBehavior(hyperTextElement)
        {
            @Override
            public void onInteract(Map<String, String> values)
            {
            
            }
        };
        Element e = new TextElement("Test");
        HyperTextBehavior hyperTextBehavior2 = new HyperTextBehavior(e)
        {
            @Override
            public void onInteract(Map<String, String> values)
            {
            
            }
        };
        
        hyperTextBehavior.add(hyperTextBehavior2);
        
        System.out.println(hyperTextBehavior.build());
        return hyperTextBehavior;
    }
    
    public static void main(String[] args) throws IOException
    {
        TestApp testApp = new TestApp();
        testApp.start(8081, 8080, "TestApp");
    }
}
