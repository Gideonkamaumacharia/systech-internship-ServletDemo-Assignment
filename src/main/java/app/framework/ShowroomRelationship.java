package app.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShowroomRelationship {
    // For One-to-Many: The column in the child table (e.g., "showroomId")
    String mappedBy() default "";
}