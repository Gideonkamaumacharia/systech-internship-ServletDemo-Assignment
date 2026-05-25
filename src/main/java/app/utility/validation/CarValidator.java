package app.utility.validation;

import app.model.Car;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.math.BigDecimal;

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
                car.getPrice() != null && car.getPrice().compareTo(BigDecimal.ZERO) > 0;

    }
    public boolean isExpensive(Car car) {
        return car != null
                && car.getPrice() != null
                && car.getPrice().compareTo(BigDecimal.valueOf(5_000_000L)) > 0;

    }
}
