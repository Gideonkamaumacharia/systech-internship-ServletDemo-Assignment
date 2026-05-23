package app.framework;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PackageScanner {

    public static List<Class<?>> getClasses(String packageName) {

        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();

        try {
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File dir = new File(resource.getFile());
                classes.addAll(findClasses(dir, packageName));
                System.out.println("Resource URL: " + resource);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to scan package: " + packageName, e);
        }

        return classes;
    }

    private static List<Class<?>> findClasses(File directory,
                                              String packageName)
            throws ClassNotFoundException {

        List<Class<?>> classes = new ArrayList<>();

        if (!directory.exists()) return classes;

        File[] files = directory.listFiles();
        if (files == null) return classes;

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file,
                        packageName + "." + file.getName()));

            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "."
                        + file.getName().substring(0,
                        file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }

        return classes;
    }
}
