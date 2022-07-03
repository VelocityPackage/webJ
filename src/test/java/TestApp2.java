import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.components.Panel;
import com.velocitypackage.webj.materials.components.items.Button;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;

import java.io.IOException;

public class TestApp2 extends WebJApplication
{
    
    @Override
    public HyperTextBehavior build()
    {
        
        
        
        return null;
    }
    
    
    public Component page1(Component ref)
    {
        return new Panel(
            new Button(
                    "to page 2", () ->
                    {
                        fromTo(ref, page2(ref));
                    }
            )
        );
    }
    
    public void fromTo(Component ref, Component to) {
        ref = to;
    }
    
    public Component page2(Component ref)
    {
        return new Panel(
                new Button(
                        "to page 1", () ->
                {
                    fromTo(ref, page1(ref));
                }
                )
        );
    }
    
    public static void main(String[] args) throws IOException
    {
        WebJApplication application = new TestApp2();
        application.start(8081, 8080, "TestApp2");
    }
}
