package app.utility.validation;

import app.model.Car;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ValidatorQualifier(ValidatorQualifier.ValidationType.CAR)
@ApplicationScoped
public class CarValidator implements Validate<Car> {

    private ValidatorHelper helper;

    @Inject
    public void setHelper(ValidatorHelper helper){
        this.helper = helper;
    }

    @Override
    public boolean isValid(Car car) {

        if (car == null) return false;

        return helper.isValidText(car.getCarModel()) &&
                helper.isValidText(car.getEngineType()) &&
                car.getYear() > 0 &&
                car.getPrice() > 0;
    }
    public boolean isExpensive(Car car) {
        return car != null
                && car.getPrice() != null
                && car.getPrice() > 5_000_000;
    }
}
