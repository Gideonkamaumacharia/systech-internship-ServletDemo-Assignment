package app.action;

import app.bean.CarBean;
import app.framework.*;
import app.model.Car;
import app.model.User;
import com.mysql.cj.PreparedQuery;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@ApplicationScoped
@ActionController
public class CarAction {

    @EJB
    private CarBean carBean;

    @Inject
    private ShowroomFramework showroomFramework;

    @ActionMapping(path = "/car/list", method = "GET")
    public ActionResponse list(HttpServletRequest req, HttpSession session) throws Exception {

        User caller = (User) session.getAttribute("activeUser");

        String showroomId  = req.getParameter("showroomId");
        String brandId     = req.getParameter("brandId");
        String categoryId  = req.getParameter("categoryId");

        List<Car> cars = carBean.getCars(showroomId, brandId, categoryId, caller);

        // Build filter form HTML + table HTML together
        String filterForm = showroomFramework.htmlFilterForm(
                req.getContextPath(),
                "/app/car/list",
                caller
        );

        String table = showroomFramework.htmlTable(Car.class, cars, req.getContextPath());

        return ActionResponse.ofHtml(filterForm + table);
    }

    @ActionMapping(path = "/car/create", method = "POST")
    public ActionResponse create(HttpServletRequest req, HttpSession session) throws Exception {
        User caller = getCallerOrThrow(session);
        Car car = showroomFramework.serializeForm(req.getParameterMap(), Car.class);
        carBean.create(car, caller);
        return ActionResponse.ofRedirect("/app/car/list");
    }

    @ActionMapping(path = "/car/edit/{id}", method = "GET")
    public ActionResponse edit(@PathVariable("id") Long id,HttpServletRequest req) throws Exception {
        Car car = carBean.findById(id);
        String html = showroomFramework.htmlEditForm(Car.class, car,req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/car/update", method = "POST")
    public ActionResponse update(HttpServletRequest req, HttpSession session) throws Exception {
        User caller = getCallerOrThrow(session);
        Car car = showroomFramework.serializeForm(req.getParameterMap(), Car.class);
        carBean.update(car, caller);
        return ActionResponse.ofRedirect("/app/car/list");
    }

    @ActionMapping(path = "/car/delete/{id}", method = "POST")
    public ActionResponse delete(@PathVariable("id") Long id, HttpSession session) throws Exception {
        User caller = getCallerOrThrow(session);
        carBean.remove(id, caller);
        return ActionResponse.ofRedirect("/app/car/list");
    }

    @ActionMapping(path = "/car/form", method = "GET")
    public ActionResponse form(HttpServletRequest req) throws Exception {
        String html = showroomFramework.htmlForm(Car.class,req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    private User getCallerOrThrow(HttpSession session) {
        if (session == null) throw new SecurityException("No active session.");
        User caller = (User) session.getAttribute("activeUser");
        if (caller == null) throw new SecurityException("Not authenticated.");
        return caller;
    }
}