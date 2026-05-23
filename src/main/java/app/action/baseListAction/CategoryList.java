//package app.action.baseListAction;
//
//import app.bean.CategoryBean;
//import app.model.Category;
//import jakarta.inject.Inject;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import java.util.List;
//
//@WebServlet("/category_list")
//public class CategoryList extends BaseListAction<Category> {
//
//    @Inject
//    private CategoryBean categoryBean;
//
//
//    @Override
//    protected List<Category> fetchList(HttpServletRequest req) throws Exception {
//        return categoryBean.getCategories();
//    }
//}
