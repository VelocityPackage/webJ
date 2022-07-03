package Version2;

import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.components.Panel;
import com.velocitypackage.webj.materials.components.items.Button;
import com.velocitypackage.webj.materials.webJ.Application;

public class TestApp extends Application
{
    public TestApp()
    {
        setApplicationName("Test");
        setRootBehavior(
                page1()
        );
    }
    
    public Component page1()
    {
        return new Panel(
                new Button(
                        "Go to page 2", () -> {
                            setRootBehavior(page2());
                }
                )
        );
    }
    
    public Component page2()
    {
        return new Panel(
                new Button(
                        "Go to page 1", () -> {
                    setRootBehavior(page1());
                }
                )
        );
    }
}
