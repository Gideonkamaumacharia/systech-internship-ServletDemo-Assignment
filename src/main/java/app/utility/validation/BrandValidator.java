package app.utility.validation;

import app.model.Brand;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ValidatorQualifier(ValidatorQualifier.ValidationType.BRAND)
@ApplicationScoped
public class BrandValidator implements Validate<Brand> {

        private ValidatorHelper helper;

        @Inject
        public void setHelper(ValidatorHelper helper){
            this.helper = helper;
        }

        @Override
        public boolean isValid(Brand brand) {

            if (brand == null) return false;

            return helper.isValidText(brand.getName()) &&
                    helper.isValidText(brand.getCountryOfOrigin());

        }
//        public boolean isExpensive(Car car) {
//            return car != null
//                    && car.getPrice() > 5_000_000;
//        }
    }

