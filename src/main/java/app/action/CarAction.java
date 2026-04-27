package app.action;

import app.model.Car;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/car")
public class CarAction extends BaseAction<Car> {
    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.CAR)
    private Validate<Car> validator;


    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Car car = serializeForm(req.getParameterMap());

        if(validator.isValid(car)){
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./car");
        }

    }
}
