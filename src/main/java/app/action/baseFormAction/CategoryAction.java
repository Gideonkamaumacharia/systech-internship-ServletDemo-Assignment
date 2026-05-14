package app.action.baseFormAction;

import app.action.BaseAction;
import app.bean.CategoryBean;
import app.framework.ShowroomTable;
import app.model.Category;
import app.model.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/category")
public class CategoryAction extends BaseFormAction<Category> {


    @Inject
    private CategoryBean categoryBean;


    @Override
    protected void persistEntity(Category category, User currentUser) throws Exception {
        categoryBean.createCategory(category, currentUser);
    }
}
