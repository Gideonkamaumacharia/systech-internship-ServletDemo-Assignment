package app.action;


import app.model.User;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
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


    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = serializeForm(req.getParameterMap());

        if(validator.isValid(user)){
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./user");
        }

    }
}