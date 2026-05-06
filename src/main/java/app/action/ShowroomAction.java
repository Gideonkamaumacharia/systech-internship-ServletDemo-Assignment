package app.action;

import app.model.Car;
import app.model.Showroom;
import app.utility.validation.Validate;
import app.utility.validation.ValidatorQualifier;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/showroom")
public class ShowroomAction extends BaseAction<Showroom> {

    @Inject
    @ValidatorQualifier(ValidatorQualifier.ValidationType.SHOWROOM)
    private Validate<Showroom> validator;


    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Showroom showroom = serializeForm(req.getParameterMap());

        if(validator.isValid(showroom)){
            super.doPost(req, resp);
        } else {
            resp.sendRedirect("./showroom");
        }

    }
}
