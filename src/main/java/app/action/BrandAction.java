package app.action;

import app.bean.BrandBean;
import app.framework.ShowroomTable;
import app.model.Brand;
import app.model.User;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/brand")
public class BrandAction extends BaseAction<Brand>{

    @EJB
    BrandBean brandBean;


    public void handleCreate(Brand brand,
                             HttpServletRequest req,
                             HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = (User) req.getSession().getAttribute("activeUser");

        try{
           brandBean.create(brand,currentUser);

            resp.sendRedirect(brand.getClass()
                    .getAnnotation(ShowroomTable.class)
                    .tableUrl());

        } catch(IllegalArgumentException e) {
            resp.sendRedirect("./brand");
        }

    }
}
