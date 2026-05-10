package app.action;

import app.bean.CarBean;
import app.framework.ShowroomTable;
import app.model.Car;
import app.model.User;
import jakarta.ejb.EJB;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//Defines business behaviour
@WebServlet("/car")
public class CarAction extends BaseAction<Car> {

    @EJB
    private CarBean carBean;

    @Override
    public void handleCreate(Car car,HttpServletRequest req, HttpServletResponse resp)
            throws  IOException {

        User currentUser = (User) req.getSession().getAttribute("activeUser");

        try{
            carBean.create(car,currentUser);

            resp.sendRedirect(car.getClass()
                    .getAnnotation(ShowroomTable.class)
                    .tableUrl());
        } catch (IllegalArgumentException e){
            resp.sendRedirect("./car");
        }

    }
}
