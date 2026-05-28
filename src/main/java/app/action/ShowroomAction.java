package app.action;

import app.bean.ShowroomBean;
import app.framework.*;
import app.model.Showroom;
import app.model.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@ApplicationScoped
@ActionController
public class ShowroomAction {

    @EJB
    private ShowroomBean showroomBean;

    @Inject
    private ShowroomFramework showroomFramework;

    @ActionMapping(path = "/showroom/list", method = "GET")
    public ActionResponse list(HttpServletRequest req) throws Exception {
        List<Showroom> showrooms = showroomBean.getShowrooms();
        return ActionResponse.ofList(Showroom.class, showrooms);
    }

    @ActionMapping(path = "/showroom/form", method = "GET")
    public ActionResponse form(HttpServletRequest req) throws Exception {
        String html = showroomFramework.htmlForm(Showroom.class, req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/showroom/create", method = "POST")
    public ActionResponse create(HttpServletRequest req,
                                 HttpSession session) throws Exception {
        Showroom showroom = showroomFramework.serializeForm(req.getParameterMap(), Showroom.class);
        User user = (User) session.getAttribute("activeUser");
        showroomBean.createShowroom(showroom, user);
        return ActionResponse.ofRedirect("/app/showroom/list");
    }

    @ActionMapping(path = "/showroom/edit/{id}", method = "GET")
    public ActionResponse edit(@PathVariable("id") Long id,
                               HttpServletRequest req) throws Exception {
        Showroom showroom = showroomBean.findById(id);
        String html = showroomFramework.htmlEditForm(Showroom.class, showroom, req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/showroom/update", method = "POST")
    public ActionResponse update(HttpServletRequest req) throws Exception {
        Showroom showroom = showroomFramework.serializeForm(req.getParameterMap(), Showroom.class);
        showroomBean.update(showroom);
        return ActionResponse.ofRedirect("/app/showroom/list");
    }

    @ActionMapping(path = "/showroom/delete/{id}", method = "POST")
    public ActionResponse delete(@PathVariable("id") Long id,HttpSession session) throws Exception {
        User caller = getCallerOrThrow(session);
        showroomBean.remove(id,caller);
        return ActionResponse.ofRedirect("/app/showroom/list");
    }





    private User getCallerOrThrow(HttpSession session) {
        if (session == null) throw new SecurityException("No active session.");
        User caller = (User) session.getAttribute("activeUser");
        if (caller == null) throw new SecurityException("Not authenticated.");
        return caller;
    }
}