package script;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class ScriptClassLoader extends URLClassLoader {

    private String classPackage;
    private ClassLoader defaultClassLoader;
    private boolean dev;

    public ScriptClassLoader(URL[] urls) {
        super(urls);
    }

    public ScriptClassLoader(URL[] urls, String classPackage, boolean dev) {
        super(urls);
        this.classPackage = classPackage;
        this.dev = dev;
    }

    public ScriptClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ScriptClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public ScriptClassLoader(String name, URL[] urls, ClassLoader parent) {
        super(name, urls, parent);
    }

    public ScriptClassLoader(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(name, urls, parent, factory);
    }
}
