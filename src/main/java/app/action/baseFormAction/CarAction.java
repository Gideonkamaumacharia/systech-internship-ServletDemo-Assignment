package app.action.baseFormAction;

import app.bean.CarBean;
import app.model.Car;
import app.model.User;
import jakarta.ejb.EJB;

import jakarta.servlet.annotation.WebServlet;

//Defines business behaviour
@WebServlet("/car")
public class CarAction extends BaseFormAction<Car> {

    @EJB
    private CarBean carBean;

    @Override
    protected void persistEntity(Car car, User currentUser) throws Exception {
        carBean.create(car,currentUser);
    }

}
