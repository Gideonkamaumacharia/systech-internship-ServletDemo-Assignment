package app.framework;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.lang.reflect.Method;
import java.util.*;

public class ActionRegistry {

    // Stores all registered path+method → ActionMap entries
    private static final List<ActionMap> registry = new ArrayList<>();

    public static void scanAndRegister(String packageName) {

        try (ScanResult scanResult =
                     new ClassGraph()
                             .acceptPackages(packageName)
                             .enableClassInfo()
                             .enableAnnotationInfo()
                             .scan()) {

            ClassInfoList controllers =
                    scanResult.getClassesWithAnnotation(
                            ActionController.class.getName());
            System.out.println("CLAZZES: " + controllers.size());

            for (ClassInfo classInfo : controllers) {

                Class<?> clazz = classInfo.loadClass();

                for (Method method : clazz.getDeclaredMethods()) {

                    if (!method.isAnnotationPresent(ActionMapping.class))
                        continue;

                    ActionMapping mapping =
                            method.getAnnotation(ActionMapping.class);

                    registry.add(new ActionMap(
                            mapping.path(),
                            mapping.method(),
                            clazz,
                            method
                    ));

                    System.out.println(
                            "Registered: "
                                    + mapping.method()
                                    + " "
                                    + mapping.path()
                    );
                }
            }
        }
    }

    public static ActionMapMatch findMatch(String requestPath, String httpMethod) {
        for (ActionMap map : registry) {

            Map<String, String> pathVariables = matches(map.getPath(), requestPath);

            if (pathVariables != null
                    && map.getHttpMethod().equalsIgnoreCase(httpMethod)) {
                return new ActionMapMatch(map, pathVariables);
            }
        }
        return null;
    }

    // Checks if a registered path pattern matches the incoming path
    // Supports path variables eg /car/edit/{id} matching /car/edit/42
    private static Map<String, String> matches(String pattern,
                                               String actualPath) {
        if (actualPath == null) return null;

        String[] patternParts = pattern.split("/");
        String[] actualParts  = actualPath.split("/");

        if (patternParts.length != actualParts.length) return null;

        Map<String, String> variables = new HashMap<>();

        for (int i = 0; i < patternParts.length; i++) {
            String p = patternParts[i];
            String a = actualParts[i];

            if (p.startsWith("{") && p.endsWith("}")) {
                // This is a path variable eg {id}
                String varName = p.substring(1, p.length() - 1);
                variables.put(varName, a);
            } else if (!p.equals(a)) {
                return null; // static segment doesn't match
            }
        }
        return variables;
    }
}


// Find all classes in app.action
// For each class with @ActionController:
//   baseUrl = annotation value e.g "/car"
//   For each method with @Action:
//     fullPath = method + ":" + baseUrl + action.path()
//     e.g "GET:/car/list"
//     registry.put(fullPath, new ActionMap(class, method))