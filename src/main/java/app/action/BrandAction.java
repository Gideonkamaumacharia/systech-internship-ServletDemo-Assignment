package app.action;

import app.model.Brand;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/brand")
public class BrandAction extends BaseAction<Brand>{
    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.BRAND)
    private Validate<Brand> validator;


    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Brand brand = serializeForm(req.getParameterMap());

        if(validator.isValid(brand)){
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./brand");
        }

    }
}
