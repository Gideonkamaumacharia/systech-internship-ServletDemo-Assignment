//package app.action.baseFormAction;
//
//import app.action.BaseAction;
//import app.framework.ShowroomTable;
//import app.model.User;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//public abstract class BaseFormAction<T> extends BaseAction<T> {
//
//    protected abstract void persistEntity(T entity, User currentUser) throws Exception;
//
//    @Override
//        protected void handleCreate(T entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        User currentUser = (User)req.getSession().getAttribute("activeUser");
//
//        try{
//            persistEntity(entity,currentUser);
//            resp.sendRedirect(
//                    getType().getAnnotation(ShowroomTable.class).tableUrl());
//
//        } catch(IllegalArgumentException e){
//            resp.sendRedirect("." + req.getServletPath());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
