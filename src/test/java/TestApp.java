import com.velocitypackage.webj.materials.components.Panel;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.tools.WebJApplication;

import java.io.IOException;

public class TestApp extends WebJApplication
{
    @Override
    public HyperTextBehavior build()
    {
        Panel panel = new Panel();
        return panel;
    }
    
    public static void main(String[] args) throws IOException
    {
        TestApp testApp = new TestApp();
        testApp.start(8080, 8081, "TEst");
    }
}
