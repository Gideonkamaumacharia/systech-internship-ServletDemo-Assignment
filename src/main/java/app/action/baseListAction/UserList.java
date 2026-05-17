package app.action.baseListAction;


import app.bean.UserBean;
import app.model.User;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;


@WebServlet("/user_list")
public class UserList extends BaseListAction<User> {

    @EJB
    private UserBean userBean;


    @Override
    protected List<User> fetchList(HttpServletRequest req) throws Exception {
        String showroomId = req.getParameter("showroom_Id");
        return userBean.getUsers(showroomId);
    }
}