package app.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ShowroomTable {
    String label();
    String tableUrl();
    String registerUrl();
    String listJsp() default "list.jsp";
    String editUrl();
    String deleteUrl();
}