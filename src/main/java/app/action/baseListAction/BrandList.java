//package app.action.baseListAction;
//
//import app.action.BaseAction;
//import app.bean.BrandBean;
//import app.framework.ShowroomTable;
//import app.model.Brand;
//import jakarta.ejb.EJB;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/brand_list")
//public class BrandList extends BaseAction<Brand> {
//
//    @EJB
//    BrandBean brandBean;
//
//    @Override
//    public void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//        List<Brand> dataList = brandBean.getbrands();
//
//        req.setAttribute("dataList",dataList);
//
//        String jspName = getType().isAnnotationPresent(ShowroomTable.class)?
//                getType().getAnnotation(ShowroomTable.class).listJsp() : "list.jsp";
//
//        req.getRequestDispatcher(jspName).forward(req,resp);
//    }
//
//}
