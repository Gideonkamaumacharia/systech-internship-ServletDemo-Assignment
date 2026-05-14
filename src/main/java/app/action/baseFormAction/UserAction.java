package app.action.baseFormAction;



import app.bean.UserBean;
import app.model.User;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;


@WebServlet("/user")
public class UserAction extends BaseFormAction<User> {

    @EJB
    private UserBean userBean;


    @Override
    protected void persistEntity(User user, User currentUser) throws Exception {
        userBean.create(user);
    }
}