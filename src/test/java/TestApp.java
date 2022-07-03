import com.velocitypackage.webj.materials.components.*;
import com.velocitypackage.webj.materials.components.forms.Form;
import com.velocitypackage.webj.materials.components.forms.FormEvent;
import com.velocitypackage.webj.materials.components.forms.Input;
import com.velocitypackage.webj.materials.components.forms.InputType;
import com.velocitypackage.webj.materials.components.icons.Icon;
import com.velocitypackage.webj.materials.components.icons.IconKey;
import com.velocitypackage.webj.materials.components.items.*;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.materials.hypertext.Style;

import java.io.File;
import java.io.IOException;

public class TestApp extends WebJApplication
{
    
    Component component;
    
    @Override
    public HyperTextBehavior build()
    {
        Component component = new Panel(
                this.component
        );
        this.component = new Panel(
                header(),
                new View(
                        new Row(
                                image(),
                                form()
                        ),
                        new Row(
                                controller(),
                                dox()
                        ),
                        new Row(
                                new Panel(
                                        dox(),
                                        dox(),
                                        dox(),
                                        dox()
                                ),
                                action()
                        )
                )
        );
        return component;
    }
    
    public Component header()
    {
        return new Panel(
                new Style[]{
                        new Style(Style.StyleIdentifier.BACKGROUND, "#202020"),
                },
                new View(
                        new Row(
                                new Panel(
                                        Align.START, Align.MIDDLE,
                                        new Heading("VelocityPackage", Heading.Type.H3,
                                                new Style(Style.StyleIdentifier.COLOR, "white")
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
                                                    component = new Panel(
                                                            header()
                                                    );
                                                }
                                        ),
                                        new Icon(IconKey.SETTINGS, new Color(255, 255, 255))
                                )
                        )
                )
        );
    }
    
    public Component image()
    {
        return new Margin(
                new Image(new File("img.png"), new Style(Style.StyleIdentifier.BORDER_RADIUS, "10px"))
        );
    }
    
    public Component form()
    {
        Input name = new Input(InputType.TEXT, null, "Name");
        Input thirdName = new Input(InputType.TEXT, null, "Third name");
        
        return new Form(new FormEvent()
        {
            @Override
            public void run()
            {
                System.out.println("New user: " + getInput(name) + " " + getInput(thirdName));
            }
        },
                new Heading("Create user: ", Heading.Type.H5),
                name,
                thirdName,
                new Input(InputType.SUBMIT, "Submit", null)
        );
    }
    
    public Component controller()
    {
        return new Panel(
                new Button(
                        "I / O", Button.ButtonType.DANGER, () -> System.out.println("on")
                ),
                new ButtonGroup(
                        new Button(
                                "Off", Button.ButtonType.SECONDARY, () -> System.out.println("on")
                        ),
                        new Button(
                                "On", Button.ButtonType.PRIMARY, () -> System.out.println("on")
                        ),
                        new Button(
                                "All off", Button.ButtonType.WARNING, () -> System.out.println("on")
                        )
                )
        );
    }
    
    public Component dox()
    {
        return new TextField(
                new Heading("Heading"),
                new Paragraph("lorem ipsum asdfadf asd lbasd fkjasd öflbaöslkd fölkasd öflkjad öflkansd fölkjas döflkjas dfölkjasd fölkad fölkjad fölkas df")
        );
    }
    
    public Component action()
    {
        return new ActionPanel(
                () -> {
                    System.out.println("Action");
                },
                new Heading("ActionPanel"),
                image()
        );
    }
    
    public static void main(String[] args) throws IOException
    {
        TestApp testApp = new TestApp();
        testApp.start(8081, 8080, "Test");
    }
}
