package app.action;

import app.framework.ActionController;
import app.framework.ActionMapping;
import app.framework.ActionResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ApplicationScoped
@ActionController
public class LogoutAction {

    @ActionMapping(path = "/logout", method = "GET")
    public ActionResponse logout(HttpServletRequest req) {

        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ActionResponse.ofRedirect("/login?msg=Logged out successfully");
    }
}