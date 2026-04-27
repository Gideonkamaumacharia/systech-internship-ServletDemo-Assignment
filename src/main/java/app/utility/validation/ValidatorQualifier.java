package app.utility.validation;

import jakarta.inject.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
public @interface ValidatorQualifier {
    ValidationType value();

    public enum ValidationType {
        CAR,
        USER,
        BRAND,
        CATEGORY,
        SHOWROOM
    }
}
