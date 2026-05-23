//package app.action.baseEditAction;
//
//import app.bean.CarBean;
//import app.model.Car;
//import jakarta.ejb.EJB;
//import jakarta.servlet.annotation.WebServlet;
//
//@WebServlet("/editCar")
//public class EditCarAction extends BaseEditAction<Car>{
//
//    @EJB
//    CarBean carBean;
//
//
//    @Override
//    protected Car findEntity(Long id) throws Exception {
//        return carBean.findById(id);
//    }
//
//    @Override
//    protected void updateEntity(Car car) throws Exception {
//        carBean.update(car);
//    }
//
//    @Override
//    protected String getAttributeName() {
//        return "car";
//    }
//
//    @Override
//    protected String getEditJsp() {
//        return "editCar.jsp";
//    }
//}
