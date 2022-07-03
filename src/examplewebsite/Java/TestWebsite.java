import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.components.Panel;
import com.velocitypackage.webj.materials.components.items.Button;
import com.velocitypackage.webj.materials.components.items.Heading;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.tools.WebJApplication;

import java.io.IOException;

public class TestWebsite extends WebJApplication
{
 
    @Override
    public HyperTextBehavior build()
    {
        Component currentPage = page1();
        Component website = new Panel(
                new Button("Switch", () -> buttonClick(currentPage)),
                currentPage
        );
        return website;
    }
    
    private void buttonClick(Component currentPage){
        currentPage = page2();
    }
    
    private Component page1(){
        return new Heading("Page 1");
    }
    
    private Component page2(){
        return new Heading("Page 2");
    }
    
    public static void main(String[] args) throws IOException
    {
        TestWebsite testWebsite = new TestWebsite();
        testWebsite.start(8080,8081, "Test");
    }
}
