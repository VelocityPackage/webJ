package Version2;

import com.velocitypackage.webj.tools.Manager;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Manager manager = new Manager(8080, 8081, new TestApp());
        manager.start();
    }
}
