package app.utility.validation;

import app.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("userBean")
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
                helper.isValidText(user.getPassword()) &&
                user.getPassword().length() >= 4 &&
                helper.isValidText(user.getRole());
    }
    public boolean isAdmin(User user) {
        return user != null &&
                user.getRole() != null &&
                user.getRole().equalsIgnoreCase("ADMIN");
    }

}