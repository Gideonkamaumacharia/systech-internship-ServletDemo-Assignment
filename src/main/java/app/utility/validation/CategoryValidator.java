package app.utility.validation;

import app.model.Brand;
import app.model.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ValidatorQualifier(ValidatorQualifier.ValidationType.CATEGORY)
@ApplicationScoped
public class CategoryValidator implements Validate<Category> {

    private ValidatorHelper helper;

    @Inject
    public void setHelper(ValidatorHelper helper){
        this.helper = helper;
    }

    @Override
    public boolean isValid(Category category) {

        if (category == null) return false;

        return helper.isValidText(category.getName()) &&
                helper.isValidText(category.getDescription());

    }


}
