package app.action;


import app.bean.CarBean;
import app.bean.UserBean;
import app.framework.ShowroomTable;
import app.model.Car;
import app.model.Showroom;
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

@WebServlet("/user")
public class UserAction extends BaseAction<User> {
    @Inject
    @ApplicationScoped
    @ValidatorQualifier(ValidatorQualifier.ValidationType.USER)
    private Validate<User> validator;

    @EJB
    UserBean userBean;


    @Override
    public void handleCreate(User user, HttpServletRequest req, HttpServletResponse resp)
            throws  IOException {

        //User currentUser = (User) req.getSession().getAttribute("activeUser");

        if(validator.isValid(user)){
            userBean.create(user);

            resp.sendRedirect(user.getClass()
                    .getAnnotation(ShowroomTable.class)
                    .tableUrl());
        } else {
            resp.sendRedirect("./user");
        }

    }
}