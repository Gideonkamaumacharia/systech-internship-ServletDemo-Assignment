package app.utility.validation;

import app.model.Car;
import app.model.Showroom;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ValidatorQualifier(ValidatorQualifier.ValidationType.SHOWROOM)
@ApplicationScoped
public class ShowroomValidator implements Validate<Showroom>{

    private ValidatorHelper helper;

    @Inject
    public void setHelper(ValidatorHelper helper){
        this.helper = helper;
    }


    @Override
    public boolean isValid(Showroom showroom) {
        if (showroom == null) return false;

        return helper.isValidText(showroom.getLocationName()) &&
                showroom.getId() > 0 &&
                showroom.getCapacity() > 0 ;
    }
}
