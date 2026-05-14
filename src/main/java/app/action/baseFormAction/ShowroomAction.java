package app.action.baseFormAction;

import app.bean.ShowroomBean;
import app.model.Showroom;
import app.model.User;
import jakarta.inject.Inject;

import jakarta.servlet.annotation.WebServlet;


@WebServlet("/showroom")
public class ShowroomAction extends BaseFormAction<Showroom> {

    @Inject
    private ShowroomBean showroomBean;


    @Override
    protected void persistEntity(Showroom showroom, User currentUser) throws Exception {
        showroomBean.createShowroom(showroom,currentUser);
    }
}
