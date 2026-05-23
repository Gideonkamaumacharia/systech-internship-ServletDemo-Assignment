//package app.action.baseDeleteAction;
//
//import app.bean.BrandBean;
//import app.model.Brand;
//import jakarta.ejb.EJB;
//import jakarta.servlet.annotation.WebServlet;
//
//@WebServlet("/deleteBrand")
//public class DeleteBrandAction extends BaseDeleteAction<Brand>{
//
//    @EJB
//    private BrandBean brandBean;
//
//    @Override
//    protected void removeEntity(Long id) throws Exception {
//        brandBean.remove(id);
//    }
//}
