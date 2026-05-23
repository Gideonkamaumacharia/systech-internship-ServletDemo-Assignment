//package app.action.baseDeleteAction;
//
//import app.bean.CarBean;
//import app.model.Car;
//import jakarta.ejb.EJB;
//import jakarta.servlet.annotation.WebServlet;
//
//@WebServlet("/deleteCar")
//public class DeleteCarAction extends BaseDeleteAction<Car> {
//
//    @EJB
//    CarBean carBean;
//
//    @Override
//    protected void removeEntity(Long id) throws Exception {
//        carBean.remove(id);
//    }
//
//
//}