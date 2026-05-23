package app.framework;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class ActionParamBinder {

    public static Object[] bind(ActionMap actionMap,
                                HttpServletRequest req,
                                HttpServletResponse resp,
                                Map<String, String> pathVariables) {

        Method      method = actionMap.getMethod();
        Parameter[] params = method.getParameters();
        Object[]    args   = new Object[params.length];

        for (int i = 0; i < params.length; i++) {
            Parameter param = params[i];
            Class<?>  type  = param.getType();

            if (type == HttpServletRequest.class) {
                args[i] = req;

            } else if (type == HttpServletResponse.class) {
                args[i] = resp;

            } else if (type == HttpSession.class) {
                args[i] = req.getSession();

            } else if (param.isAnnotationPresent(PathVariable.class)) {
                // extract from path eg {id} in /car/edit/{id}
                String varName = param.getAnnotation(PathVariable.class).value();
                String value   = pathVariables.get(varName);
                args[i] = convertTo(value, type);

            } else if (param.isAnnotationPresent(RequestParam.class)) {
                // extract from query string or form body
                String paramName = param.getAnnotation(RequestParam.class).value();
                String value     = req.getParameter(paramName);
                args[i] = convertTo(value, type);
            }
        }

        return args;
    }

    private static Object convertTo(String value, Class<?> type) {
        if (value == null) return null;
        if (type == Long.class   || type == long.class)   return Long.parseLong(value);
        if (type == Integer.class || type == int.class)   return Integer.parseInt(value);
        if (type == String.class)                          return value;
        return value;
    }
}
