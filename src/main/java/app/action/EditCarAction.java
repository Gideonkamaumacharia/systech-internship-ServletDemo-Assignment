//package app.action;
//
//import app.bean.CarBean;
//import app.model.Car;
//import jakarta.ejb.EJB;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.*;
//
//import java.io.IOException;
//
//@WebServlet("/editCar")
//public class EditCarAction extends BaseAction<Car> {
//
//    @EJB
//    private CarBean carBean;
//
//    @Override
//    public void doGet(HttpServletRequest req,
//                         HttpServletResponse resp)
//            throws ServletException, IOException {
//
//        Long id = Long.parseLong(req.getParameter("id"));
//
//        Car car = carBean.findById(id);
//
//        req.setAttribute("car", car);
//
//        req.getRequestDispatcher("editCar.jsp")
//                .forward(req, resp);
//    }
//}
