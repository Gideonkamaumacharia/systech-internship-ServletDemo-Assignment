package app.utility.validation;

import app.model.User;
import app.model.enums.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("userValidator")
@ApplicationScoped
@ValidatorQualifier(ValidatorQualifier.ValidationType.USER)
public class UserValidator implements Validate<User> {

    private final ValidatorHelper helper;

    @Inject
    public UserValidator(ValidatorHelper helper){
        this.helper = helper;
    }

    @Override
    public boolean isValid(User user) {
        if (user == null) return false;

        return helper.isValidText(user.getUsername()) &&
                helper.isValidText(user.getPasswordHash()) &&
                user.getPasswordHash().length() >= 4 &&
                user.getRole() != null;

    }
    public boolean isAdmin(User user) {
        return user != null &&
                user.getRole() != null &&
                user.getRole() == UserRole.ADMIN;

    }

}