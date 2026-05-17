package app.action;

import app.bean.CarBean;
import app.model.Car;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteCar")
public class DeleteCarAction extends BaseAction<Car> {


    @EJB
    CarBean carBean;

    @Override
    public void doPost(HttpServletRequest req,
                       HttpServletResponse resp)
            throws IOException {

        Long id =
                Long.parseLong(req.getParameter("id"));

        carBean.remove(id);

        resp.sendRedirect("list");
    }
}