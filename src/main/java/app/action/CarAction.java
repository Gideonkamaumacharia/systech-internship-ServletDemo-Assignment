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
    public ActionResponse list(HttpServletRequest req) throws Exception {
        List<Car> cars = carBean.findAll();
        return ActionResponse.ofList(Car.class, cars);
    }

    @ActionMapping(path = "/car/form", method = "GET")
    public ActionResponse form(HttpServletRequest req) throws Exception {
        String html = showroomFramework.htmlForm(Car.class,req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/car/create", method = "POST")
    public ActionResponse create(HttpServletRequest req,
                                 HttpSession session) throws Exception {
        Car  car  = showroomFramework.serializeForm(req.getParameterMap(), Car.class);
        User user = (User) session.getAttribute("activeUser");
        carBean.create(car, user);
        return ActionResponse.ofRedirect("/app/car/list");
    }

    @ActionMapping(path = "/car/edit/{id}", method = "GET")
    public ActionResponse edit(@PathVariable("id") Long id,HttpServletRequest req) throws Exception {
        Car car = carBean.findById(id);
        String html = showroomFramework.htmlEditForm(Car.class, car,req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/car/update", method = "POST")
    public ActionResponse update(HttpServletRequest req) throws Exception {
        Car car = showroomFramework.serializeForm(req.getParameterMap(), Car.class);
        carBean.update(car);
        System.out.println("CAR TO UPDATE ID: "+ car.getId());
        return ActionResponse.ofRedirect("/app/car/list");
    }

    @ActionMapping(path = "/car/delete/{id}", method = "POST")
    public ActionResponse delete(@PathVariable("id") Long id) throws Exception {
        carBean.remove(id);
        return ActionResponse.ofRedirect("/app/car/list");
    }
}