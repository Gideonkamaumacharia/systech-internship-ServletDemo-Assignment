package app.action;

import app.bean.CarBean;
import app.model.Car;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/updateCar")
public class UpdateCarAction extends BaseAction<Car> {

    @EJB
    CarBean carBean;

    @Override
    protected void handleCreate(Car car,
                               HttpServletRequest req,
                               HttpServletResponse resp)
            throws IOException {

        carBean.update(car);

        resp.sendRedirect("list");
    }
}
