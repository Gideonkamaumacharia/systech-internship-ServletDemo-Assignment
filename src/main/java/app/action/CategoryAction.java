package app.action;

import app.model.Brand;
import app.model.Category;
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
    @ValidatorQualifier(ValidatorQualifier.ValidationType.CATEGORY)
    private Validate<Category> validator;


    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Category category = serializeForm(req.getParameterMap());

        if(validator.isValid(category)){
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./category");
        }

    }
}
