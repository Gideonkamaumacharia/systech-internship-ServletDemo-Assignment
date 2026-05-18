package app.action.baseDeleteAction;

import app.action.BaseAction;
import app.framework.ShowroomTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseDeletionAction<T> extends BaseAction<T> {

    protected  abstract void removeEntity(Class<T> entity,Long id) throws Exception;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id =
                Long.parseLong(req.getParameter("id"));

       Class<T> entity = getType();

        try{
            removeEntity(entity, id);
        }catch (Exception e) {
            throw new ServletException("Failed to delete entity", e);
        }

        String redirectUrl = getType().isAnnotationPresent(ShowroomTable.class)
                ? getType().getAnnotation(ShowroomTable.class).tableUrl()
                : "list";

        resp.sendRedirect(redirectUrl);
    }

}
