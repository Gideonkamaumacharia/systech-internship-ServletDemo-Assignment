package app.action;

import app.bean.CarBean;
import app.framework.ShowroomTable;
import app.model.Car;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/list")
public class CarList extends BaseAction<Car> {

    @EJB
    CarBean carBean;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String showroomId = req.getParameter("showroomId");

        List<Car> dataList = carBean.getCars(showroomId);

        req.setAttribute("dataList",dataList);

        String jspName = getType().isAnnotationPresent(ShowroomTable.class)?
                getType().getAnnotation(ShowroomTable.class).listJsp() : "list.jsp";

        req.getRequestDispatcher(jspName).forward(req,resp);
    }

    }
