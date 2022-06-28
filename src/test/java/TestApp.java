import com.velocitypackage.webj.materials.components.*;
import com.velocitypackage.webj.materials.components.items.Button;
import com.velocitypackage.webj.materials.components.items.ButtonGroup;
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
                new Column(
                    new Panel(
                            new Style[]{
                                    new Style(Style.StyleIdentifier.BORDER_RADIUS, "10px"),
                                    new Style(Style.StyleIdentifier.BACKGROUND, new Color(40, 40, 40).getHex()),
                                    new Style(Style.StyleIdentifier.MARGIN, "7px")
                            },
                            new Row(
                                    new Panel(
                                            Align.LEFT,
                                            new Button("Left", () -> System.out.println("Left")),
                                            new ButtonGroup(
                                                    new Button("Delete", Button.ButtonType.DANGER, ()  -> System.out.println("Delete")),
                                                    new Button("Save", Button.ButtonType.SUCCESS, () -> System.out.println("Save")),
                                                    new Button("Wait", Button.ButtonType.SECONDARY, () -> System.out.println("Wait"))
                                            )
                                    ),
                                    new Panel(
                                            Align.CENTER,
                                            new Button("Middle", () -> System.out.println("Middle"))
                                    ),
                                    new Panel(
                                            Align.RIGHT,
                                            new Button("Right", () -> System.out.println("Right"))
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
