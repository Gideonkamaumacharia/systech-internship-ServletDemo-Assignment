package app.action;

import app.bean.ShowroomBean;
import app.framework.ShowroomTable;
import app.model.Showroom;
import app.model.User;
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
    ShowroomBean showroomBean;

    public void handleCreate(Showroom showroom,HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = (User)req.getSession().getAttribute("activeUser");

        try{
            showroomBean.createShowroom(showroom,currentUser);

            resp.sendRedirect(showroom.getClass()
                    .getAnnotation(ShowroomTable.class)
                    .tableUrl());
        } catch(IllegalArgumentException e) {
            resp.sendRedirect("./showroom");
        }

    }
}
