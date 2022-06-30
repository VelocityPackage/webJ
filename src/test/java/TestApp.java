import com.velocitypackage.webj.materials.components.*;
import com.velocitypackage.webj.materials.components.items.Button;
import com.velocitypackage.webj.materials.components.items.Heading;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.materials.hypertext.Style;
import com.velocitypackage.webj.tools.WebJApplication;

import java.io.IOException;

public class TestApp extends WebJApplication
{
    
    @Override
    public HyperTextBehavior build()
    {
        return new Panel(
            header()
        );
    }
    
    public Component header() {
        return new Panel(
                new Style[]{
                        new Style(Style.StyleIdentifier.BACKGROUND, "#202020"),
                },
                new View(
                        new Row(
                                new Panel(
                                        Align.START, Align.MIDDLE,
                                        new Heading("VelocityPackage",
                                                new Style(Style.StyleIdentifier.COLOR, "white"),
                                                new Style(Style.StyleIdentifier.MARGIN_TOP, "10px"),
                                                new Style(Style.StyleIdentifier.MARGIN_LEFT, "10px")
                                        )
                                ),
                                new Panel(
                                        Align.END, Align.MIDDLE,
                                        new Button(
                                                "Home",
                                                new Style[]{
                                                        new Style(Style.StyleIdentifier.COLOR, "white"),
                                                        new Style(Style.StyleIdentifier.TEXT_DECORATION, "none")
                                                },
                                                Button.ButtonType.LINK,
                                                () -> {
                                                    System.out.println("Home");
                                                }
                                        ),
                                        new Button(
                                                "Projects",
                                                new Style[]{
                                                        new Style(Style.StyleIdentifier.COLOR, "white"),
                                                        new Style(Style.StyleIdentifier.TEXT_DECORATION, "none")
                                                },
                                                Button.ButtonType.LINK,
                                                () -> {
                                                    System.out.println("Projects");
                                                }
                                        )
                                )
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
