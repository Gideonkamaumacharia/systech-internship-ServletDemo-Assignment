package app.action;

import app.bean.CategoryBean;
import app.framework.ShowroomTable;
import app.model.Category;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/category_list")
public class CategoryList extends BaseAction<Category>{

    @Inject
    CategoryBean categoryBean;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> dataList = categoryBean.getCategories();

        req.setAttribute("dataList",dataList);

        String jspName = getType().isAnnotationPresent(ShowroomTable.class)?
                getType().getAnnotation(ShowroomTable.class).listJsp() : "list.jsp";

        req.getRequestDispatcher(jspName).forward(req,resp);
    }
}
