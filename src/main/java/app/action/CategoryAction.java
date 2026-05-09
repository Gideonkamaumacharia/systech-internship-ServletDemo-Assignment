package app.action;

import app.bean.CategoryBean;
import app.framework.ShowroomTable;
import app.model.Brand;
import app.model.Category;
import app.model.User;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/category")
public class CategoryAction extends BaseAction<Category>{


    @Inject
    CategoryBean categoryBean;

    public void handleCreate(Category category,HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    User currentUser = (User)req.getSession().getAttribute("activeUser");

        try{
            categoryBean.createCategory(category,currentUser);

            resp.sendRedirect(category.getClass()
                                        .getAnnotation(ShowroomTable.class)
                                        .tableUrl());
        } catch(IllegalArgumentException e) {
            resp.sendRedirect("./category");
        }

    }
}
