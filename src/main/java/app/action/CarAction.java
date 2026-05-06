package app.action;

import app.bean.CarBean;
import app.framework.ShowroomTable;
import app.model.Car;
import app.model.User;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.EJB;
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

    @EJB
    private CarBean carBean;

    @Override
    public void handleCreate(Car car,HttpServletRequest req, HttpServletResponse resp)
            throws  IOException {

        User currentUser = (User) req.getSession().getAttribute("activeUser");

        if(validator.isValid(car)){
            carBean.create(car,currentUser);

            resp.sendRedirect(car.getClass()
                    .getAnnotation(ShowroomTable.class)
                    .tableUrl());
        } else {
            resp.sendRedirect("./car");
        }

    }
}
