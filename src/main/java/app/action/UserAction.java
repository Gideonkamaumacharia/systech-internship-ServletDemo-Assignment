package app.action;

import app.bean.UserBean;
import app.framework.ActionController;
import app.framework.ActionMapping;
import app.framework.ActionResponse;
import app.framework.ShowroomFramework;
import app.model.Car;
import app.model.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@ApplicationScoped
@ActionController()
public class UserAction {

    @EJB
    private UserBean userBean;

    @Inject
    private ShowroomFramework showroomFramework;

    @ActionMapping(path = "/user/list", method = "GET")
    public ActionResponse list(HttpServletRequest req) throws Exception {
        List<User> users = userBean.getUsers();
        return ActionResponse.ofList(User.class, users);
    }

    @ActionMapping(path = "/user/form", method = "GET")
    public ActionResponse form(HttpServletRequest req) throws Exception {
        String html = showroomFramework.htmlForm(User.class,req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/user/create", method = "POST")
    public ActionResponse create(HttpServletRequest req,
                                 HttpSession session) throws Exception {
        User user  = showroomFramework.serializeForm(req.getParameterMap(), User.class);
        userBean.create(user);
        return ActionResponse.ofRedirect("/app/user/list");
    }
}
