package app.action;

import app.bean.BrandBean;
import app.framework.*;
import app.model.Brand;
import app.model.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@ApplicationScoped
@ActionController
public class BrandAction {

    @EJB
    private BrandBean brandBean;

    @Inject
    private ShowroomFramework showroomFramework;

    @ActionMapping(path = "/brand/list", method = "GET")
    public ActionResponse list(HttpServletRequest req) throws Exception {
        List<Brand> brands = brandBean.getbrands();
        return ActionResponse.ofList(Brand.class, brands);
    }

    @ActionMapping(path = "/brand/form", method = "GET")
    public ActionResponse form(HttpServletRequest req) throws Exception {
        String html = showroomFramework.htmlForm(Brand.class, req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/brand/create", method = "POST")
    public ActionResponse create(HttpServletRequest req,
                                 HttpSession session) throws Exception {
        Brand brand = showroomFramework.serializeForm(req.getParameterMap(), Brand.class);
        User user = (User) session.getAttribute("activeUser");
        brandBean.create(brand, user);
        return ActionResponse.ofRedirect("/app/brand/list");
    }

    @ActionMapping(path = "/brand/edit/{id}", method = "GET")
    public ActionResponse edit(@PathVariable("id") Long id,
                               HttpServletRequest req) throws Exception {
        Brand brand = brandBean.findById(id);
        String html = showroomFramework.htmlEditForm(Brand.class, brand, req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/brand/update", method = "POST")
    public ActionResponse update(HttpServletRequest req) throws Exception {
        Brand brand = showroomFramework.serializeForm(req.getParameterMap(), Brand.class);
        brandBean.update(brand);
        return ActionResponse.ofRedirect("/app/brand/list");
    }

    @ActionMapping(path = "/brand/delete/{id}", method = "POST")
    public ActionResponse delete(@PathVariable("id") Long id) throws Exception {
        brandBean.remove(id);
        return ActionResponse.ofRedirect("/app/brand/list");
    }
}