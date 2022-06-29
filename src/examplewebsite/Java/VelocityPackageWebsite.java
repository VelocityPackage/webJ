import com.velocitypackage.webj.materials.components.Align;
import com.velocitypackage.webj.materials.components.Component;
import com.velocitypackage.webj.materials.components.Panel;
import com.velocitypackage.webj.materials.components.Row;
import com.velocitypackage.webj.materials.components.items.Button;
import com.velocitypackage.webj.materials.components.items.Heading;
import com.velocitypackage.webj.materials.components.items.Image;
import com.velocitypackage.webj.materials.hypertext.Bootstrap;
import com.velocitypackage.webj.materials.hypertext.HyperTextBehavior;
import com.velocitypackage.webj.tools.WebJApplication;

import java.io.File;
import java.io.IOException;

public class VelocityPackageWebsite extends WebJApplication
{
    private Panel heading;
    private Component body;
    
    @Override
    public HyperTextBehavior build()
    {
        this.navigateProjekte();
        return new Panel(
                heading = new Panel(
                        new Bootstrap[]{
                                Bootstrap.BG_DARK,
                                Bootstrap.PX_5,
                                Bootstrap.PY_2,
                        },
                        new Row(
                                new Panel(new Heading("VelocityPackage", Heading.Type.H1, Bootstrap.TEXT_LIGHT)),
                                new Panel(
                                        new Bootstrap[]{
                                                Bootstrap.ALIGN_BOTTOM
                                        },
                                        Align.RIGHT,
                                        new Button("VelocityPackage", new Bootstrap[]{Bootstrap.TEXT_LIGHT}, Button.ButtonType.LINK, this::navigateProjekte),
                                        new Button("Projekte", new Bootstrap[]{Bootstrap.TEXT_LIGHT}, Button.ButtonType.LINK, this::navigateProjekte),
                                        new Button("Blog", new Bootstrap[]{Bootstrap.TEXT_LIGHT}, Button.ButtonType.LINK, this::navigateProjekte),
                                        new Button("Kontakt",new Bootstrap[]{Bootstrap.TEXT_LIGHT},  Button.ButtonType.LINK, this::navigateProjekte)
                                )
                        )
                ),
                body
        );
    }
    
    private void navigateProjekte(){
        body = new Image(new File("img.png"));
    }
    
    public static void main(String[] args) throws IOException
    {
        VelocityPackageWebsite velocityPackageWebsite = new VelocityPackageWebsite();
        velocityPackageWebsite.start(8080, 8081, "VelocityPackage");
    }
}
