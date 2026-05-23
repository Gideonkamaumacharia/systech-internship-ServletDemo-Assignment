//package app.action.baseDeleteAction;
//
//import app.bean.CategoryBean;
//import app.model.Brand;
//import app.model.Category;
//import jakarta.ejb.EJB;
//import jakarta.servlet.annotation.WebServlet;
//
//@WebServlet("/deleteCategory")
//public class DeleteCategoryAction extends BaseDeleteAction<Category>{
//
//    @EJB
//    private CategoryBean categoryBean;
//
//    @Override
//    protected void removeEntity( Long id) throws Exception {
//        categoryBean.remove(id);
//    }
//
//}
