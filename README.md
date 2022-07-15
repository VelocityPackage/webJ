# WebJ
> Framework for Java based webpages.

### LowLevelUI

UI element

```Java
HyperTextElememt hyperTextElement = new HyperTextElement(
        Tag.DIV, 
        new Bootstrap[]{}, 
        new Attribute[]{}, 
        new Style[]{}
        );
```

UI behavior

```Java
HyperTextBehavior hyperTextBehavior1 = new HyperTextBehavior(){};

hyperTextBehavior.setHyperTextElement(
        hyperTextElement
        );
```

UI behavior add child element

```Java
HyperTextBehavior hyperTextBehavior2 = new HyperTextBehavior(){};
hyperTextBehavior2.setHyperTextElement(
        hyperTextElement
        );
        
hyperTextBehavior2.addChild(hyperTextBehavior1);
```

Create a HyperTextPage

```Java
String path = "/"; //root path
HyperTextPage hyperTextPage = new HyperTextPage(
        path,
        hyperTextBehavior2
        );
```

Create application with pages

```Java
public class YourApplication extends Application
{
    public YourApplication()
    {
        setApplicationName("YourApplicationName");
        setFavicon(new File("/path_to_favicon"));
        addPage(hyperTextPage);
    }
}
```

### EntryPoint | Server Management

```Java
public class Main
{
    public static void main(String... args) throws IOException
    {
        int port = 8080;
        new WebJManager(port, new YourApplication()).start();
    }
}
```
