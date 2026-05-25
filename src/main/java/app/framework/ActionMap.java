package app.framework;

import java.lang.reflect.Method;

public class ActionMap {

    private final String path;//  "/car/form"  ->"/car/create"
    private final String httpMethod;// POST
    private final Class<?> action;       // eg CarAction.class
    private final Method method;         // eg the create() method

    public ActionMap(String path,
                     String httpMethod,
                     Class<?> action,
                     Method method) {
        this.path       = path;
        this.httpMethod = httpMethod;
        this.action     = action;
        this.method     = method;
    }

    public String getPath(){
        return path; //
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public Class<?> getAction(){
        return action;
    }

    public Method getMethod(){
        return method;
    }
}