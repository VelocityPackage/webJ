import com.velocitypackage.webj.materials.components.Panel;
import com.velocitypackage.webj.materials.components.Row;
import com.velocitypackage.webj.materials.components.items.Button;
import com.velocitypackage.webj.materials.components.items.Heading;
import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.tools.WebJApplication;

import java.io.IOException;

public class TestApp extends WebJApplication
{
    
    
    @Override
    public HyperTextBehavior build()
    {
        return new Panel(
                new Bootstrap[]{
                        Bootstrap.BG_DARK,
                        Bootstrap.PY_2,
                        Bootstrap.PX_2
                },
                new Row(
                        new Panel(
                            new Heading("VelocityPackage", Bootstrap.TEXT_BLACK)
                        ),
                        new Panel(
                            new Button("Home", () -> {}),
                            new Button("Bla", () -> {}),
                            new Button("Bls", () -> {})
                        )
                )
        );
    }
    
    public static void main(String[] args) throws IOException
    {
        TestApp testApp = new TestApp();
        testApp.start(8081, 8080, "Test");
    }
}
