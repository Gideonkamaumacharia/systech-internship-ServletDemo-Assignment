package app.utility.validation;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ValidatorHelper {

    public boolean isValidText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}