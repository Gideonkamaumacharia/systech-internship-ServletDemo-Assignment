package app.framework;

import app.model.enums.UserRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ShowroomSecured {
    UserRole[] readRoles()  default {};   // who can READ
    UserRole[] writeRoles() default {};   // who can CREATE/EDIT/DELETE
    boolean adminOnly()     default false;
    boolean readOnly()      default false;
}