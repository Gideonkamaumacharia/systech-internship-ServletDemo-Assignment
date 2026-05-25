package app.action;

import app.bean.CategoryBean;
import app.framework.*;
import app.model.Category;
import app.model.User;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@ApplicationScoped
@ActionController
public class CategoryAction {

    @EJB
    private CategoryBean categoryBean;

    @Inject
    private ShowroomFramework showroomFramework;

    @ActionMapping(path = "/category/list", method = "GET")
    public ActionResponse list(HttpServletRequest req) throws Exception {
        List<Category> categories = categoryBean.getCategories();
        return ActionResponse.ofList(Category.class, categories);
    }

    @ActionMapping(path = "/category/form", method = "GET")
    public ActionResponse form(HttpServletRequest req) throws Exception {
        String html = showroomFramework.htmlForm(Category.class, req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/category/create", method = "POST")
    public ActionResponse create(HttpServletRequest req,
                                 HttpSession session) throws Exception {
        Category category = showroomFramework.serializeForm(req.getParameterMap(), Category.class);
        User user = (User) session.getAttribute("activeUser");
        categoryBean.createCategory(category, user);
        return ActionResponse.ofRedirect("/app/category/list");
    }

    @ActionMapping(path = "/category/edit/{id}", method = "GET")
    public ActionResponse edit(@PathVariable("id") Long id,
                               HttpServletRequest req) throws Exception {
        Category category = categoryBean.findById(id);
        String html = showroomFramework.htmlEditForm(Category.class, category, req.getContextPath());
        return ActionResponse.ofHtml(html);
    }

    @ActionMapping(path = "/category/update", method = "POST")
    public ActionResponse update(HttpServletRequest req) throws Exception {
        Category category = showroomFramework.serializeForm(req.getParameterMap(), Category.class);
        categoryBean.update(category);
        return ActionResponse.ofRedirect("/app/category/list");
    }

    @ActionMapping(path = "/category/delete/{id}", method = "POST")
    public ActionResponse delete(@PathVariable("id") Long id) throws Exception {
        categoryBean.remove(id);
        return ActionResponse.ofRedirect("/app/category/list");
    }
}