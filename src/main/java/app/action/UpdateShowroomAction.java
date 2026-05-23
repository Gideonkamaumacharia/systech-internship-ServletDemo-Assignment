//package app.action;
//
//import app.bean.CarBean;
//import app.bean.ShowroomBean;
//import app.model.Car;
//import app.model.Showroom;
//import jakarta.ejb.EJB;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@WebServlet("/updateShowroom")
//public class UpdateShowroomAction extends BaseAction<Showroom> {
//
//    @EJB
//   private ShowroomBean showroomBean;
//
//    @Override
//    protected void handleCreate(Showroom showroom,
//                                HttpServletRequest req,
//                                HttpServletResponse resp)
//            throws IOException {
//
//        showroomBean.update(showroom);
//
//        resp.sendRedirect("showroom_list");
//    }
//}
