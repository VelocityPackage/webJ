import com.velocitypackage.webj.materials.components.Panel;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.tools.WebJApplication;

import java.io.IOException;

public class TestApp extends WebJApplication
{
    @Override
    public HyperTextBehavior build()
    {
        return new Panel();
    }
    
    public static void main(String[] args) throws IOException
    {
        TestApp testApp = new TestApp();
        testApp.start(8081, 8080, "Test");
    }
}
